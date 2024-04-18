package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Produto;
import models.Usuario;

public class GerenciadorDeProdutos {
	private static final String arquivo = "Produtos.txt";

	public void verificar(String arquivo) {
		File nomeArquivo = new File(arquivo);

		if (nomeArquivo.exists()) {
			System.out.println("Bacno de Dados funcionando.");
		} else {
			try {
				nomeArquivo.createNewFile();
				System.out.println("Arquivo Criado.");
			} catch (IOException e) {
				System.out.println("Ocorreu um erro ao criar arquivo." + e.getMessage());
			}
		}

	}

	public void addProduct(Produto produto) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo, true))) {
			bw.write(produto.toString());
			bw.newLine();
		} catch (IOException e) {
			System.out.println("Ocorreu um erro ao escrever no arquivo: " + e.getMessage());
		}
	}

	public List<Produto> readerProduct() {
		List<Produto> produto = new ArrayList<Produto>();

		try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
			String linha; // linha =>1;nome;senha

			// percorrer todas as linhas enquanto seja siferente de vazio
			while ((linha = br.readLine()) != null) {
				// Split => vai dividir em tres partes, vai cortar o ";".
				String[] partes = linha.split(";");
				produto.add(new Produto(Long.parseLong(partes[0]), partes[1], Double.parseDouble(partes[2]),
						Integer.parseInt(partes[3])));
			}

		} catch (IOException e) {
			System.out.println("Ocorreu um erro ao ler arquivo: " + e.getMessage());
		}
		return produto;
	}

	public void reescreverArquivo(List<Produto> produto) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
			for (Produto prod : produto) {
				bw.write(prod.toString());
				bw.newLine();
			}
		} catch (IOException e) {
			System.out.println("Ocorreu um erro ao reescrever o arquivo: " + e.getMessage());
		}

	}

	public void updateProduct(long id, String novoNome, double novoPreco, int novaQuantidade) {
		List<Produto> produto = readerProduct();
		boolean encontrado = false;

		for (Produto prod : produto) {
			if (prod.getId() == id) {
				prod.setNome(novoNome);
				prod.setPreco(novoPreco);
				prod.setId(novaQuantidade);
				encontrado = true;
				break;
			}
		}

		if (encontrado) {
			reescreverArquivo(produto);
			System.out.println("Produto editado com sucesso.");
		} else {
			System.out.println("Produto não encontrado.");
		}

	}

	public void deletProducts(long id) {

		List<Produto> produtos = readerProduct();

		// removeIf => é um loop, um forEach simplificado
		// o usuario ele vai de linha em linha até ser igual ao que o usuario digi
		if (produtos.removeIf(produto -> produto.getId() == id)) {
			reescreverArquivo(produtos);
			System.out.println("Usuario deletado com sucesso.");
		} else {
			System.out.println("Usuario não encontrado.");
		}
	}

}
