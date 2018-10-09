package br.com.alura.loja;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;


public class Servidor {

	public static void main(String[] args) throws IOException {
		HttpServer server = inicializaServidor();
		System.out.println("Servidor Rodando");
		System.in.read(); // Esperando dar enter para sair do servidor 
		server.stop();
	}

	
	static HttpServer inicializaServidor() {
		//Tudo que estiver dentro desse pacote ele ura o JAX-RS para o servidor 
		ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");
		//Porta em que ele vai usar 
		URI uri = URI.create("http://localhost:8080/"); 
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
		
		return server;
	}
}
 