#include "WiFiEsp.h"
#include "SoftwareSerial.h"

#define PINO_SENSOR_LUZ A0
#define PINO_SENSOR_TEMPERATURA A1

SoftwareSerial esp8266(8,9);
const char* ssid = "Solarium";
const char* pass = "papaicris";
int status = WL_IDLE_STATUS;

WiFiEspServer server(80);
RingBuffer buf(8);

void setup() 
{ 
  Serial.begin(115200);  // porta de debug
  esp8266.begin(9600);
  WiFi.init(&esp8266);
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

  pinMode(PINO_SENSOR_LUZ, INPUT);
  pinMode(PINO_SENSOR_TEMPERATURA, INPUT);
}

void loop() 
{
  WiFiEspClient client = server.available();
  if (client){
    Serial.println("Novo cliente");
    buf.init();

    while (client.connected())
    {
      if (client.available())
      {
        float luz;
        float temperatura;
        
        char c = client.read();
        buf.push(c);

        luz = analogRead(PINO_SENSOR_LUZ);
        Serial.print("Luz: ");
        Serial.println(luz);
        client.print(temperatura);

        client.print("|");
        
        temperatura = (float(analogRead(PINO_SENSOR_TEMPERATURA))*5/(1023))/0.01;
        Serial.print("Temperatura: ");
        Serial.println(temperatura);
        client.print(temperatura);
        
        if (buf.endsWith("\r\n\r\n"))
        {
          Serial.println("Fechando"); 
          break;
        }
      }
    } // while
    client.stop();
    Serial.println("Desconectado");
  }
}
