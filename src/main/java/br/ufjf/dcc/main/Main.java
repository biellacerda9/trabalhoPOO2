package br.ufjf.dcc.main;

import br.ufjf.dcc.app.AtivoApp;
import br.ufjf.dcc.io.Menu;

public class Main {
    static void main() {
        AtivoApp.carregarArquivosIniciais();
        Menu.exibirMenuPrincipal();
    }
}
