package com.example.appandroidfotovoltaica.ui.clientes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appandroidfotovoltaica.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ClientesFragment extends Fragment {

    private ClientesViewModel galleryViewModel;

    private EditText
        etNomeCliente,
        etDataNascimentoCliente,
        etEmailCliente,
        etTelefoneCliente,
        etCpfCliente;

    private Button btnAdicionarCliente;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(ClientesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_clientes, container, false);
        /*final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        this.etNomeCliente = (EditText) root.findViewById(R.id.etNomeCliente);
        this.etDataNascimentoCliente = (EditText) root.findViewById(R.id.etDataNascimentoCliente);
        this.etEmailCliente = (EditText) root.findViewById(R.id.etEmailCliente);
        this.etTelefoneCliente = (EditText) root.findViewById(R.id.etTelefoneCliente);
        this.etCpfCliente = (EditText) root.findViewById(R.id.etCpfCliente);

        this.btnAdicionarCliente = (Button) root.findViewById(R.id.btnAdicionarCliente);

        final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());
        final String URL = "http://177.220.18.82:3000/insert_cliente";

        this.btnAdicionarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    String nome = etNomeCliente.getText().toString().trim();
                    String data = etDataNascimentoCliente.getText().toString();
                    String email = etEmailCliente.getText().toString();
                    String telefone = etTelefoneCliente.getText().toString();
                    String cpf = etCpfCliente.getText().toString();

                    HashMap<String, String> params = new HashMap<String,String>();
                    params.put("email", email);
                    params.put("nome", nome);
                    params.put("telefone", telefone);
                    params.put("data", data);
                    params.put("cpf", cpf);

                    JsonObjectRequest jsObjRequest = new
                            JsonObjectRequest(
                                    Request.Method.POST,
                                    URL,
                                    new JSONObject(params),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                            public void onResponse(JSONObject response) {
                                                Toast.makeText(
                                                        getActivity().getApplicationContext(),
                                                        "Cliente inserido",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(
                                                    getActivity().getApplicationContext(),
                                                    error.getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                    });
                    QUEUE.add(jsObjRequest);
                }
                catch(Exception exc)
                {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Digite os dados corretamente",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }
}