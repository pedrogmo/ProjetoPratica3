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
}
