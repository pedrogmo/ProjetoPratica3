package com.example.appandroidfotovoltaica.ui.mainactivity.clientes.adicionarcliente;

import com.android.volley.toolbox.StringRequest;
import com.example.appandroidfotovoltaica.classes.cliente.Cliente;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.appandroidfotovoltaica.ui.mainactivity.MainActivity;
import com.example.appandroidfotovoltaica.classes.verificadora.mensagenserro.mensagenserrocliente.MensagensErroCliente;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.ui.mainactivity.clientes.ClientesFragment;

import java.util.HashMap;
import java.util.Map;

public class AdicionarClienteFragment extends Fragment {

    private EditText
        etNomeCliente,
        etDataNascimentoCliente,
        etEmailCliente,
        etTelefoneCliente,
        etCpfCliente;

    private TextView
        tvExceptionNome,
        tvExceptionData,
        tvExceptionEmail,
        tvExceptionTelefone,
        tvExceptionCpf;

    private Button btnAdicionarCliente;
    private MensagensErroCliente mensagens;
    private String textoAnterior = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_adicionarcliente, container, false);

        this.etNomeCliente = (EditText) root.findViewById(R.id.etNomeClienteAdd);
        this.etDataNascimentoCliente = (EditText) root.findViewById(R.id.etDataNascimentoClienteAdd);
        etDataNascimentoCliente.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String texto = charSequence.toString();
                if (textoAnterior.length() < texto.length()){
                    if (texto.length() == 2 || texto.length() == 5)
                        etDataNascimentoCliente.append("/");
                }
                else
                if (texto.length() >=1 && texto.charAt(texto.length() - 1) == '/'){
                    texto = texto.substring(0,texto.length() - 1);
                    etDataNascimentoCliente.setText(texto);
                    etDataNascimentoCliente.setSelection(texto.length());
                }

                textoAnterior = texto;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        this.etEmailCliente = (EditText) root.findViewById(R.id.etEmailClienteAdd);
        this.etTelefoneCliente = (EditText) root.findViewById(R.id.etTelefoneClienteAdd);
        this.etCpfCliente = (EditText) root.findViewById(R.id.etCpfClienteAdd);

        this.tvExceptionNome = (TextView) root.findViewById(R.id.tvExceptionNomeClienteAdd);
        this.tvExceptionData = (TextView) root.findViewById(R.id.tvExceptionDataClienteAdd);
        this.tvExceptionEmail = (TextView) root.findViewById(R.id.tvExceptionEmailClienteAdd);
        this.tvExceptionTelefone = (TextView) root.findViewById(R.id.tvExceptionTelefoneClienteAdd);
        this.tvExceptionCpf = (TextView) root.findViewById(R.id.tvExceptionCpfClienteAdd);

        this.btnAdicionarCliente = (Button) root.findViewById(R.id.btnAdicionarCliente);

        this.mensagens = new MensagensErroCliente(
            this.tvExceptionNome,
            this.tvExceptionData,
            this.tvExceptionEmail,
            this.tvExceptionTelefone,
            this.tvExceptionCpf);

        this.btnAdicionarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nome = etNomeCliente.getText().toString().trim();
                final String data = etDataNascimentoCliente.getText().toString().trim();
                final String email = etEmailCliente.getText().toString().trim();
                final String telefone = etTelefoneCliente.getText().toString().trim();
                final String cpf = etCpfCliente.getText().toString().trim();

                if (mensagens.teveMensagensDeErro(nome, data, email, telefone, cpf))
                    return;

                MyTask task = new MyTask(Cliente[].class);
                task.execute(Enderecos.GET_CLIENTE_EMAIL + "/" + email);
                while (task.isTrabalhando()) ;
                Cliente[] resultClientes = (Cliente[]) task.getDados();
                if (resultClientes.length > 0)
                {
                    Toast.makeText(
                        getActivity().getApplicationContext(),
                        "Email já cadastrado",
                        Toast.LENGTH_SHORT).show();
                    return;
                }

                final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());
                StringRequest postRequest = new StringRequest(
                    Request.Method.POST,
                    Enderecos.POST_CLIENTE,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Toast.makeText(
                                getActivity().getApplicationContext(),
                                "Cliente inserido",
                                Toast.LENGTH_SHORT).show();

                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_adicionarcliente, new ClientesFragment());
                            fragmentTransaction.commit();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    "Erro ao inserir cliente",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", email);
                        params.put("nome", nome);
                        params.put("telefone", telefone);
                        params.put("data", data);
                        params.put("cpf", cpf);
                        params.put("codEmpresa", ((MainActivity)getActivity()).getUsuario().getCodEmpresa() + "");
                        return params;
                    }
                };
                QUEUE.add(postRequest);
            }
        });

        return root;
    }


}