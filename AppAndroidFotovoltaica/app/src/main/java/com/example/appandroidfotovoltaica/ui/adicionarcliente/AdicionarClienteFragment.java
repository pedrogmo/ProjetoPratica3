package com.example.appandroidfotovoltaica.ui.adicionarcliente;

import com.android.volley.toolbox.StringRequest;
import com.example.appandroidfotovoltaica.Cliente;
import com.example.appandroidfotovoltaica.Enderecos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appandroidfotovoltaica.MyTask;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.Verificadora;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdicionarClienteFragment extends Fragment {

    private AdicionarClienteViewModel galleryViewModel;

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
    private FloatingActionButton fab;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(AdicionarClienteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_adicionarcliente, container, false);
        /*final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        fab = root.findViewById(R.id.fabNovoCliente);
        this.etNomeCliente = (EditText) root.findViewById(R.id.etNomeCliente);
        this.etDataNascimentoCliente = (EditText) root.findViewById(R.id.etDataNascimentoCliente);
        this.etEmailCliente = (EditText) root.findViewById(R.id.etEmailCliente);
        this.etTelefoneCliente = (EditText) root.findViewById(R.id.etTelefoneCliente);
        this.etCpfCliente = (EditText) root.findViewById(R.id.etCpfCliente);

        this.tvExceptionNome = (TextView) root.findViewById(R.id.tvExceptionNomeCliente);
        this.tvExceptionData = (TextView) root.findViewById(R.id.tvExceptionDataCliente);
        this.tvExceptionEmail = (TextView) root.findViewById(R.id.tvExceptionEmailCliente);
        this.tvExceptionTelefone = (TextView) root.findViewById(R.id.tvExceptionTelefoneCliente);
        this.tvExceptionCpf = (TextView) root.findViewById(R.id.tvExceptionCpfCliente);

        this.btnAdicionarCliente = (Button) root.findViewById(R.id.btnAdicionarCliente);

        final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());

        this.btnAdicionarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nome = etNomeCliente.getText().toString().trim();
                final String data = etDataNascimentoCliente.getText().toString();
                final String email = etEmailCliente.getText().toString();
                final String telefone = etTelefoneCliente.getText().toString();
                final String cpf = etCpfCliente.getText().toString();

                if (teveMensagensDeErro(nome, data, email, telefone, cpf))
                    return;

                MyTask task = new MyTask(Cliente[].class);
                task.execute(Enderecos.GET_CLIENTES + "_email/" + email);
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


                HashMap<String, String> params = new HashMap<String,String>();

                StringRequest postRequest = new StringRequest(
                        Request.Method.POST,
                        Enderecos.POST_CLIENTES,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        "Cliente inserido",
                                        Toast.LENGTH_SHORT).show();
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
                        params.put("codEmpresa", "2");
                        return params;
                    }
                };
                QUEUE.add(postRequest);
            }
        });

        return root;
    }

    private void limparMensagens()
    {
        tvExceptionNome.setText("");
        tvExceptionData.setText("");
        tvExceptionEmail.setText("");
        tvExceptionTelefone.setText("");
        tvExceptionCpf.setText("");
    }

    private boolean teveMensagensDeErro(
        String nome,
        String data,
        String email,
        String telefone,
        String cpf)
    {
        limparMensagens();
        boolean teveMensagem = false;
        if (!Verificadora.isNomeValido(nome)){
            tvExceptionNome.setText("Nome inválido. Números e simbolos não podem ser utilizados. O tamanho do nome deve ser de 10 a 50 caracteres");
            teveMensagem = true;
        }
        if (!Verificadora.isDataValida(data))
        {
            tvExceptionData.setText("Data inválida. Siga o formato dd/mm/aaaa (Exemplo: 24/09/1977)");
            teveMensagem = true;
        }
        if (!Verificadora.isEmailValido(email))
        {
            tvExceptionEmail.setText("Email inválido.");
        }
        if (!Verificadora.isTelefoneValido(telefone))
        {
            tvExceptionTelefone.setText("Telefone inválido. O número poderá ter entre 9 e 11* caracteres." +
                    "*: código de país incluso");
            teveMensagem = true;
        }
        if (!Verificadora.isCpfValido(cpf))
        {
            tvExceptionCpf.setText("Cpf inválido. O formato de um cpf é 000.000.000-00");
            teveMensagem = true;
        }

        return teveMensagem;
    }
}