package backEnd;

import application.InfoCenter;
import application.TileBoard;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class JogoDaVelha {
	
	private boolean[][] tabuleiroA;
	private boolean[][] posicoesOcupadas;
	private boolean PLAYER;
	private boolean ehVezDoJogador;
	private int profundidade;
	private boolean jogadorComeca = true;
	private GridPane tabuleiro = new GridPane();
	
	public JogoDaVelha () {
		this.tabuleiroA = new boolean[3][3];
		this.posicoesOcupadas = new boolean[3][3];
	}
	
	
	

	
	public int minimax(boolean[][] estado, boolean[][] posicoesOcupadas, int profundidade, boolean ehMaximizador) {
		
		if(jogoTerminou(estado, posicoesOcupadas)) {
			int resultado = avaliar(estado, posicoesOcupadas);
			if (resultado == 1) {
		        return 10 - profundidade; // vitória mais cedo = melhor
		    } else if (resultado == -1) {
		        return -10 + profundidade; // derrota mais tarde = melhor
		    } else {
		        return 0; // empate
		    }
		}
		
		if(ehMaximizador) {
			int melhorValor = Integer.MIN_VALUE;
			for(int i=0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					if(!posicoesOcupadas[i][j]) {
						int[] jogada = {i,j};
						boolean[][] novoEstado = aplicarJogada(estado, jogada, Constants.MAX);
						boolean[][] novaOcupacao = aplicarOcupacao(posicoesOcupadas, jogada);
						int valor = minimax(novoEstado, novaOcupacao, profundidade + 1, Constants.MIN);
						if(valor > melhorValor) melhorValor = valor;
					}
				}
			}
			return melhorValor;
		}else {
			int melhorValor = Integer.MAX_VALUE;
			for(int i=0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					if(!posicoesOcupadas[i][j]) {
						int[] jogada = {i,j};
						boolean[][] novoEstado = aplicarJogada(estado, jogada, Constants.MIN);
						boolean[][] novaOcupacao = aplicarOcupacao(posicoesOcupadas, jogada);
						int valor = minimax(novoEstado, novaOcupacao, profundidade + 1, Constants.MAX);
						if(valor < melhorValor) melhorValor = valor;
					}
					
				}
			}
			return melhorValor;
		}
	}
	
	public int[] escolherMelhorJogada(boolean[][] estadoAtual, boolean[][] posicoesOcupadas, int profundidade) {
		int melhorValor = Integer.MIN_VALUE;
		int[] melhorJogada = null;
		
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				if(!posicoesOcupadas[i][j]) {
					int[] jogada = {i,j};
					boolean[][] novoEstado = aplicarJogada(estadoAtual, jogada, Constants.MAX);
					boolean[][] novaOcupacao = aplicarOcupacao(posicoesOcupadas, jogada);
					int valor = minimax(novoEstado, novaOcupacao, profundidade + 1, Constants.MIN);
					
					if(valor > melhorValor) {
						melhorValor = valor;
						melhorJogada = jogada;
					}
				}	
			}
		}
		
		return melhorJogada;
	}
	
	
	public boolean[][] aplicarJogada(boolean[][] estado, int[] jogada, boolean PLAYER) {
		// TODO Auto-generated method stub
		boolean estadoNovo[][] = copiarMatriz(estado);
		estadoNovo[jogada[0]][jogada[1]] = PLAYER;
		return estadoNovo;
	}
	
	public boolean[][] aplicarOcupacao(boolean[][] posicoesOcupadas, int[] jogada){
		boolean novasPosicoesOcupadas[][] = copiarMatriz(posicoesOcupadas);
		novasPosicoesOcupadas[jogada[0]][jogada[1]] = true;
		return novasPosicoesOcupadas;
	}
	
	public int[] fazerJogadaDoComputador() {
		int[] jogada = escolherMelhorJogada(this.tabuleiroA, this.posicoesOcupadas, profundidade);
		this.tabuleiroA[jogada[0]][jogada[1]] = Constants.MAX;
		this.posicoesOcupadas[jogada[0]][jogada[1]] = true;
		profundidade++;
		return jogada;
	    
	}
	
	public void aguardarVezDoJogador() {
		this.ehVezDoJogador = true;
	}
	
	
	public void setProfundidade(int nivel) {
		this.profundidade = nivel;
	}

	
	public boolean marcarPosicaoUsuario(int i, int j) {
		if(this.posicoesOcupadas[i][j]) {
			System.out.println("ja esta ocupado");
			return false;
		}else {
			this.posicoesOcupadas[i][j] = true;
			//System.out.println("Esta livre");
			this.tabuleiroA[i][j] = Constants.MIN;
			return true;
		}
	}
	
	
	public static boolean[][] copiarMatriz(boolean[][] original) {
	    boolean[][] copia = new boolean[3][3];

	    for (int i = 0; i < 3; i++) {
	        System.arraycopy(original[i], 0, copia[i], 0, 3);
	    }

	    return copia;
	}
	
	public void reiniciar() {
		for(int i=0; i<3; i++) {
			for(int j=0;j<3;j++) {
				this.posicoesOcupadas[i][j] = false;
				this.tabuleiroA[i][j] = false;
			}
		}
	}
	
	
	
	
	public boolean jogoTerminou(boolean[][] estado,boolean[][]posicoesOcupadas) {
		if(posicoesOcupadas[0][0] && posicoesOcupadas[1][0] && posicoesOcupadas[2][0] && estado[0][0] && estado[1][0] && estado[2][0]) {		
			return true;
			
		}else if(posicoesOcupadas[0][0] && posicoesOcupadas[1][0] && posicoesOcupadas[2][0] && !estado[0][0] && !estado[1][0] && !estado[2][0]) {		
			return true;
			
		}else if(posicoesOcupadas[0][1] && posicoesOcupadas[1][1] && posicoesOcupadas[2][1] && estado[0][1] && estado[1][1] && estado[2][1]) {		
			return true;
			
		}else if(posicoesOcupadas[0][1] && posicoesOcupadas[1][1] && posicoesOcupadas[2][1] && !estado[0][1] && !estado[1][1] && !estado[2][1]) {		
			return true;
			
		}else if(posicoesOcupadas[0][2] && posicoesOcupadas[1][2] && posicoesOcupadas[2][2] && estado[0][2] && estado[1][2] && estado[2][2]) {;
			return true;
		}
		else if(posicoesOcupadas[0][2] && posicoesOcupadas[1][2] && posicoesOcupadas[2][2] && !estado[0][2] && !estado[1][2] && !estado[2][2]) {		
			return true;
			
		}else if(posicoesOcupadas[0][0] && posicoesOcupadas[0][1] && posicoesOcupadas[0][2] && estado[0][0] && estado[0][1] && estado[0][2]) {		
			return true;			
		
		}else if(posicoesOcupadas[0][0] && posicoesOcupadas[0][1] && posicoesOcupadas[0][2] && !estado[0][0] && !estado[0][1] && !estado[0][2]) {				
			return true;
			
		}else if(posicoesOcupadas[1][0] && posicoesOcupadas[1][1] && posicoesOcupadas[1][2] && estado[1][0] && estado[1][1] && estado[1][2]) {		
			return true;
			
		}else if(posicoesOcupadas[1][0] && posicoesOcupadas[1][1] && posicoesOcupadas[1][2] && !estado[1][0] && !estado[1][1] && !estado[1][2]) {		
			return true;
			
		}else if(posicoesOcupadas[2][0] && posicoesOcupadas[2][1] && posicoesOcupadas[2][2] && estado[2][0] && estado[2][1] && estado[2][2]) {		
			return true;
			
		}else if(posicoesOcupadas[2][0] && posicoesOcupadas[2][1] && posicoesOcupadas[2][2] && !estado[2][0] && !estado[2][1] && !estado[2][2]) {		
			return true;
			
		}else if(posicoesOcupadas[0][0] && posicoesOcupadas[1][1] && posicoesOcupadas[2][2] && estado[0][0] && estado[1][1] && estado[2][2]) {		
			return true;
			
		}else if(posicoesOcupadas[0][0] && posicoesOcupadas[1][1] && posicoesOcupadas[2][2] && !estado[0][0] && !estado[1][1] && !estado[2][2]) {		
			return true;
			
		}else if(posicoesOcupadas[0][2] && posicoesOcupadas[1][1] && posicoesOcupadas[2][0] && estado[0][2] && estado[1][1] && estado[2][0]) {		
			return true;
			
		}else if(posicoesOcupadas[0][2] && posicoesOcupadas[1][1] && posicoesOcupadas[2][0] && !estado[0][2] && !estado[1][1] && !estado[2][0]) {		
			return true;
			
		}else {
			for(int i=0; i<=2;i++) {
				for(int j=0; j<=2;j++){
					if(posicoesOcupadas[i][j] == false) {
						return false;
					}					
				}
			}
			
			
		}		
		return true;
	}
	
	
	
	
	
	
	
	public int avaliar(boolean[][] estado, boolean[][]posicoesOcupadas) {
		if(posicoesOcupadas[0][0] && posicoesOcupadas[1][0] && posicoesOcupadas[2][0] && estado[0][0] && estado[1][0] && estado[2][0]) {		
			return 1;
			
		}else if(posicoesOcupadas[0][0] && posicoesOcupadas[1][0] && posicoesOcupadas[2][0] && !estado[0][0] && !estado[1][0] && !estado[2][0]) {		
			return -1;
			
		}else if(posicoesOcupadas[0][1] && posicoesOcupadas[1][1] && posicoesOcupadas[2][1] && estado[0][1] && estado[1][1] && estado[2][1]) {		
			return 1;
			
		}else if(posicoesOcupadas[0][1] && posicoesOcupadas[1][1] && posicoesOcupadas[2][1] && !estado[0][1] && !estado[1][1] && !estado[2][1]) {		
			return -1;
			
		}else if(posicoesOcupadas[0][2] && posicoesOcupadas[1][2] && posicoesOcupadas[2][2] && estado[0][2] && estado[1][2] && estado[2][2]) {;
			return 1;
		}
		else if(posicoesOcupadas[0][2] && posicoesOcupadas[1][2] && posicoesOcupadas[2][2] && !estado[0][2] && !estado[1][2] && !estado[2][2]) {		
			return -1;
			
		}else if(posicoesOcupadas[0][0] && posicoesOcupadas[0][1] && posicoesOcupadas[0][2] && estado[0][0] && estado[0][1] && estado[0][2]) {		
			return 1;			
		
		}else if(posicoesOcupadas[0][0] && posicoesOcupadas[0][1] && posicoesOcupadas[0][2] && !estado[0][0] && !estado[0][1] && !estado[0][2]) {				
			return -1;
			
		}else if(posicoesOcupadas[1][0] && posicoesOcupadas[1][1] && posicoesOcupadas[1][2] && estado[1][0] && estado[1][1] && estado[1][2]) {		
			return 1;
			
		}else if(posicoesOcupadas[1][0] && posicoesOcupadas[1][1] && posicoesOcupadas[1][2] && !estado[1][0] && !estado[1][1] && !estado[1][2]) {		
			return -1;
			
		}else if(posicoesOcupadas[2][0] && posicoesOcupadas[2][1] && posicoesOcupadas[2][2] && estado[2][0] && estado[2][1] && estado[2][2]) {		
			return 1;
			
		}else if(posicoesOcupadas[2][0] && posicoesOcupadas[2][1] && posicoesOcupadas[2][2] && !estado[2][0] && !estado[2][1] && !estado[2][2]) {		
			return -1;
			
		}else if(posicoesOcupadas[0][0] && posicoesOcupadas[1][1] && posicoesOcupadas[2][2] && estado[0][0] && estado[1][1] && estado[2][2]) {		
			return 1;
			
		}else if(posicoesOcupadas[0][0] && posicoesOcupadas[1][1] && posicoesOcupadas[2][2] && !estado[0][0] && !estado[1][1] && !estado[2][2]) {		
			return -1;
			
		}else if(posicoesOcupadas[0][2] && posicoesOcupadas[1][1] && posicoesOcupadas[2][0] && estado[0][2] && estado[1][1] && estado[2][0]) {		
			return 1;
			
		}else if(posicoesOcupadas[0][2] && posicoesOcupadas[1][1] && posicoesOcupadas[2][0] && !estado[0][2] && !estado[1][1] && !estado[2][0]) {		
			return -1;
			
		}else {
			return 0;
		}
	}

}
