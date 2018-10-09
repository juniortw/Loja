package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import junit.framework.Assert;

public class ClienteTest {
	
	private HttpServer server;
	private WebTarget target;
	private Client cliente;
	
	@Test
	public void buscaCarrinho(){
		
		//Criando novo cliente
		cliente = ClientBuilder.newClient();
		
		//Especificando a requisição que vai ser realizada a consulta 
		WebTarget target = cliente.target("http://localhost:8080");
		
		//Requisição sera feita com essa uri. faça uma requisição .request() e pega os dados do servidor com o .get()
		String conteudo = target.path("/carrinhos/1").request().get(String.class);
		
		//SERIALIZANDO O DADO
		//Printa no console o XML
		//System.out.println(conteudo);
		
		//Printa no console o Json
		System.out.println(new Gson().toJson(conteudo));
				
				
		// Verificando se na requisição "conteudo" contem essa string
		//Como esse pedaço do xml começa com a tag deixarei isto bem claro em nosso assert
		//Assert.assertTrue(conteudo.contains("<rua>Rua Vergueiro 3185")); 
		
		//DESERIALIZANDO O DADO
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
		
	}
	
/*	@Test
	public void testaNovosCarrinhos() {
		Carrinho carrinho = new Carrinho();
		carrinho.adiciona(new Produto(314, "Teclado", 25.70, 5));
		carrinho.setRua("Rua F 143");
		carrinho.setCidade("Francisco Morato");
		String xml = carrinho.ToXML(); 
		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		
		Response response = target.path("/carrinhos").request().post(entity);
		Assert.assertEquals(201, response.getStatus());
		String location = response.getHeaderString("Location");
		String conteudo = client.target(location).request().get(String.class);
		Assert.assertTrue(conteudo.contains("Microfone"));
	}*/
	
	
	//Levanta servidor
	@Before 
	public void startaServidor() {
		server = Servidor.inicializaServidor();
		// Criando uma configuração de cliente
		ClientConfig config = new ClientConfig();
		// Criando API de Log
		config.register(new LoggingFilter());
		this.cliente = ClientBuilder.newClient(config);
		this.target = cliente.target("http://localhost:8080");
	}
	
	//Derruba servidor 
	@After
	public void mataServidor() {
		server.stop();
	}

}
