package com.example.appandroidfotovoltaica.ui.loginactivity.empresaindividual;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.empresa.Empresa;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.classes.usuario.Usuario;

import java.util.ArrayList;

public class EmpresaIndividualFragment extends Fragment {

    private EmpresaIndividualViewModel mViewModel;

    private CheckBox chkTipoUsuario;
    private ListView lvListaUsuarios;

    private Empresa empresaAtual;
    private Usuario[] usuariosTotal;
    private ArrayList<Usuario> listaUsuarios;

    public static EmpresaIndividualFragment newInstance() {
        return new EmpresaIndividualFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                ViewModelProviders.of(this).get(EmpresaIndividualViewModel.class);
        View root = inflater.inflate(R.layout.fragment_empresaindividual, container, false);

        Bundle b = this.getArguments();
        this.empresaAtual = (Empresa) b.getSerializable("empresa");

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

        return root;
    }

    private void atualizaLista()
    {
        this.lvListaUsuarios.setAdapter(
            new UsuarioArrayAdapter(
                    getActivity().getApplicationContext(), this.listaUsuarios)
        );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EmpresaIndividualViewModel.class);
        // TODO: Use the ViewModel
    }

}
