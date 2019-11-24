package com.example.appandroidfotovoltaica.ui.loginactivity.empresaindividual;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.adapters.usuarioarrayadapter.UsuarioArrayAdapter;
import com.example.appandroidfotovoltaica.classes.constantesdetransicao.ConstantesDeTransicao;
import com.example.appandroidfotovoltaica.classes.empresa.Empresa;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.classes.usuario.Usuario;
import com.example.appandroidfotovoltaica.ui.loginactivity.usuarioempresa.UsuarioEmpresaFragment;

import java.util.ArrayList;

public class EmpresaIndividualFragment extends Fragment {

    private CheckBox chkTipoUsuario;
    private ListView lvListaUsuarios;

    private static Empresa empresaAtual;
    private Usuario[] usuariosTotal;
    private ArrayList<Usuario> listaUsuarios;

    public static EmpresaIndividualFragment newInstance() {
        return new EmpresaIndividualFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_empresaindividual, container, false);

        try
        {
            Bundle b = this.getArguments();
            Empresa e = (Empresa) b.getSerializable("empresa");
            this.empresaAtual = e;
        }
        catch(Exception exc){}

        this.chkTipoUsuario = (CheckBox) root.findViewById(R.id.chkTipoUsuario);
        this.lvListaUsuarios = (ListView) root.findViewById(R.id.lvListaUsuarios);

        MyTask task = new MyTask(Usuario[].class);
        task.execute(Enderecos.GET_USUARIO);
        while(task.isTrabalhando()) ;
        this.usuariosTotal = (Usuario[]) task.getDados();

        this.listaUsuarios = new ArrayList<Usuario>();
        for(Usuario u : usuariosTotal)
            if (this.empresaAtual.getCodigo() == u.getCodEmpresa() &&
                u.isPermitido())
            {
                this.listaUsuarios.add(u);
            }

        atualizaLista();

        this.chkTipoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean exibirNaoConfirmados = ((CheckBox) view).isChecked();

                listaUsuarios.clear();
                for(Usuario u : usuariosTotal)
                    if (empresaAtual.getCodigo() == u.getCodEmpresa())
                    {
                        if (exibirNaoConfirmados && !u.isPermitido())
                            listaUsuarios.add(u);

                        else if (!exibirNaoConfirmados && u.isPermitido())
                            listaUsuarios.add(u);
                    }

                atualizaLista();
            }
        });

        this.lvListaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
                AdapterView<?> adapterView,
                View view,
                int i,
                long l)
            {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario", listaUsuarios.get(i));
                UsuarioEmpresaFragment fragment = new UsuarioEmpresaFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_empresaindividual, fragment, ConstantesDeTransicao.F_USUARIO_EMPRESA).addToBackStack(ConstantesDeTransicao.M_USUARIO_EMPRESA).commit();
            }
        });

        return root;
    }

    private void atualizaLista()
    {
        this.lvListaUsuarios.setAdapter(
            new UsuarioArrayAdapter(
                    getActivity().getApplicationContext(), this.listaUsuarios)
        );
    }

}
