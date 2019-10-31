package com.example.appandroidfotovoltaica.classes.enderecos;

public class Enderecos
{
    private static final String IP_PORTA = "http://177.220.18.82:3000/";

    public static final String GET_CLIENTE = IP_PORTA + "cliente";
    public static final String GET_CLIENTE_EMAIL = GET_CLIENTE + "_email";
    public static final String POST_CLIENTE = IP_PORTA + "insert_cliente";
    public static final String PATCH_CLIENTE = IP_PORTA + "update_cliente";
    public static final String DELETE_CLIENTE = IP_PORTA + "delete_cliente";

    public static final String GET_USUARIO = IP_PORTA + "usuario";
    public static final String GET_USUARIO_EMAIL = GET_USUARIO + "_email";
    public static final String POST_USUARIO = IP_PORTA + "insert_usuario";
    public static final String PATCH_USUARIO = IP_PORTA + "update_usuario";
    public static final String PERMITIR_USUARIO = IP_PORTA + "permitir_usuario";
    public static final String DELETE_USUARIO = IP_PORTA + "delete_usuario";

    public static final String GET_EMPRESA = IP_PORTA + "empresa";
    public static final String POST_EMPRESA = IP_PORTA + "insert_empresa";

    public static final String GET_MODULO = IP_PORTA + "modulo";
    public static final String POST_MODULO = IP_PORTA + "insert_modulo";
    public static final String PATCH_MODULO = IP_PORTA + "update_modulo";
    public static final String DELETE_MODULO = IP_PORTA + "delete_modulo";

    public static final String GET_INVERSOR = IP_PORTA + "inversor";
    public static final String POST_INVERSOR = IP_PORTA + "insert_inversor";
    public static final String PATCH_INVERSOR = IP_PORTA + "update_inversor";
    public static final String DELETE_INVERSOR = IP_PORTA + "delete_inversor";

    public static final String GET_STRINGBOX = IP_PORTA + "stringbox";
    public static final String POST_STRINGBOX = IP_PORTA + "insert_stringbox";
    public static final String PATCH_STRINGBOX = IP_PORTA + "update_stringbox";
    public static final String DELETE_STRINGBOX = IP_PORTA + "delete_stringbox";

    public static final String POST_FIXACAO = IP_PORTA + "insert_fixacao";
    public static final String GET_FIXACAO = IP_PORTA + "fixacao";
    public static final String PATCH_FIXACAO = IP_PORTA + "update_fixacao";
    public static final String DELETE_FIXACAO = IP_PORTA + "delete_fixacao";

    public static final String GET_BOMBASOLAR = IP_PORTA + "bombasolar";
    public static final String POST_BOMBASOLAR = IP_PORTA + "insert_bombasolar";
    public static final String PATCH_BOMBASOLAR = IP_PORTA + "update_bombasolar";
    public static final String DELETE_BOMBASOLAR = IP_PORTA + "delete_bombasolar";

    public static final String GET_CABO = IP_PORTA + "cabo";
    public static final String POST_CABO = IP_PORTA + "insert_cabo";
    public static final String PATCH_CABO = IP_PORTA + "update_cabo";
    public static final String DELETE_CABO = IP_PORTA + "delete_cabo";
}
