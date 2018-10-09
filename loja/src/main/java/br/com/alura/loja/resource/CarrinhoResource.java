package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.ResponseDefault;

@Path("carrinhos") //Qual a URI do servidor
public class CarrinhoResource {
	// Quando se trabalha com o GET ele só aceita um tipo de MediaType
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String deserializandoXml(@PathParam("id") long id) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		return carrinho.ToXML();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response serializandoXml(String conteudo) {
		Carrinho carrinho = (Carrinho)new XStream().fromXML(conteudo);
		new CarrinhoDAO().adiciona(carrinho);
		URI uri = URI.create("/carrinhos/"+carrinho.getId());
		return Response.created(uri).build();
	}
	
/*	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String deserializandoJson(@PathParam("id") long id) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		return carrinho.ToJson();
	}
	*/
	@POST
	@Consumes(MediaType.APPLICATION_JSON) //Obriga o método a receber um Json 
	public Response serializandoJson(String conteudo) {
		Carrinho carrinho = new Carrinho(); 
		new CarrinhoDAO().adiciona(new Gson().fromJson(conteudo, Carrinho.class));
		//A classe ResponseDefault é para retornar no servidor uma resposta
		URI uri = URI.create("/carrinhos/"+carrinho.getId());
		return Response.created(uri).build(); //Retorna o Status Code
		//return new Gson().toJson(new ResponseDefault(201, "Created")); 
	}
	
	
	@Path("{id}/produtos/{produtoId}")
	@DELETE
	public Response removeProsuto(@PathParam("id") long id, @PathParam("produtoId") long produtoId ) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		carrinho.remove(produtoId);
		return Response.ok().build();
	}
	
	@Path("{id}/produtos/{produtoId}/quantidade")
	@PUT
	public Response alterarProsuto(String conteudo, @PathParam("id") long id, @PathParam("produtoId") long produtoId ) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		Produto produto = (Produto) new XStream().fromXML(conteudo);
		carrinho.trocaQuantidade(produto);
		return Response.ok().build();
	}
	
	

// ------------------------------------------------------------------------------------------------------------ //	

/*	
    @Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON) //MediaType que vai pro servidor
	//@PathParam busca o carrinho com a quele id como parametro na url 
	public String busca(@PathParam("id") long id) { 
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		return carrinho.ToJson();//Tipo do retorno 
	}


/*	
	@POST
	@Produces(MediaType.APPLICATION_XML)
	public String adiciona(String conteudo) {
		Carrinho carrinho = (Carrinho)new XStream().fromXML(conteudo);
		new CarrinhoDAO().adiciona(carrinho);
		return "<status>Sucesso</status>";
	}
*/	
	
	
}
