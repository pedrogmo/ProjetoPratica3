package com.example.appandroidfotovoltaica;

public class Enderecos
{
    private static final String IP_PORTA = "http://177.220.18.81:3000/";

    public static final String POST_CLIENTES = IP_PORTA + "insert_cliente";
    public static final String GET_CLIENTES = IP_PORTA + "clientes";
    public static final String PATCH_CLIENTES = IP_PORTA + "update_cliente";
    public static final String DELETE_CLIENTES = IP_PORTA + "delete_cliente";

    public static final String POST_USUARIOS = IP_PORTA + "insert_usuario";
    public static final String GET_USUARIOS = IP_PORTA + "usuarios";
    public static final String PATCH_USUARIOS = IP_PORTA + "update_usuario";
    public static final String DELETE_USUARIOS = IP_PORTA + "delete_usuario";

    public static final String POST_EMPRESAS = IP_PORTA + "insert_empresa";
    public static final String GET_EMPRESAS = IP_PORTA + "empresas";

    public static final String POST_MODULOS = IP_PORTA + "insert_modulo";
    public static final String GET_MODULOS = IP_PORTA + "modulos";
    public static final String PATCH_MODULOS = IP_PORTA + "update_modulo";
    public static final String DELETE_MODULOS = IP_PORTA + "delete_modulo";

    public static final String POST_INVERSORES = IP_PORTA + "insert_inversor";
    public static final String GET_INVERSORES = IP_PORTA + "inversores";
    public static final String PATCH_INVERSORES = IP_PORTA + "update_inversor";
    public static final String DELETE_INVERSORES = IP_PORTA + "delete_inversor";
}
