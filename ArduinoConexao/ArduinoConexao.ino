#include "WiFiEsp.h"
#include "SoftwareSerial.h"

SoftwareSerial Serial1(8,9);
char ssid[] = "AndroidAP";
char pass[] = "nnru1342";
int status = WL_IDLE_STATUS;

WiFiEspServer server(80);
RingBuffer buf(8);

void setup() 
{ 
  Serial.begin(115200);  // porta de debug
  Serial1.begin(9600);
  WiFi.init(&Serial1);
  if (WiFi.status() == WL_NO_SHIELD) {
    Serial.println("Sem modulo");
    while(true);  //trava a execução 
  }
  while (status != WL_CONNECTED){
    Serial.println("tentando conectar...");
    status = WiFi.begin(ssid,pass);
  }
  Serial.println("Conectado...");
  IPAddress ip = WiFi.localIP();
  Serial.println(ip);
  server.begin();
}

void loop() 
{
  WiFiEspClient client = server.available();
  if (client){
    Serial.println("Novo cliente");
    buf.init();

    while (client.connected()){
      if (client.available()){
        char c = client.read();
        buf.push(c);
        Serial.write(c);
        if (buf.endsWith("\r\n\r\n")){
          Serial.println("Fechando"); 
          break;
        }

        if(buf.endsWith("string")){
          buf.reset();
        }
        
      }
    } // while
    client.stop();
    Serial.println("Desconectado");
  }
}
