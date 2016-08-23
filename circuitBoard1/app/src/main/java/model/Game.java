package model;

public class Game {
	public static final int noColor = -1;
	public final char MAXCOLOR = 4;
	public Node [][] board;
	public int max_x_size;
	public int max_y_size;
	public static String[] colors = {"RED", "YELLOW", "GREEN", "BLUE"};

	public Game(int x, int y) {
		init(x,y);
	}

	//verifica se a casa pertence ao tabuleiro e se é valida para receber ligacoes
	private boolean validLocation(int x, int y ) {
		return x>=0 && x<max_x_size && y>=0 && y<max_y_size;
	}
	//cria uma ligacao entre uma casa n e a casa adjacente na direccao d
	public void addConnection(Node n ,Direction d){
		if(validConnection(n, d)){
			Node n2 = step(n,d);
			if(directionChanged(n,n2))changeDirections(n2);
			n2.color = n.color;
			n.to = d;
			n2.from = d.opposite();
		}
		
	}
	//devolve a casa adjacente a l na direccao d, ou l caso esteja no limite
	public Node step (Location l, Direction d){
		Node l2 = new Node(l.y+d.dy,l.x+d.dx);
		if (validLocation(l2.y, l2.x)) return board[l2.y][l2.x];
		else return board[l.y][l.x];
	}

	//verifica se a ligacao dada e valida, confirmando a natureza e estado de ambas as casas
	public boolean validConnection(Node l, Direction d){
			Node l1 = l;
			Node l2 = step(l, d);
			if( l1 instanceof Bridge && ((Bridge)l1).receiver )return false;
			return (l1.validConnection(d) && l2.validConnection(d.opposite()) && validColors(l1,l2));	
	}
	//remove uma ligacao, encontrando o fim dela e comecando a remover a partir desse ponto
	public void removeConnection(Node l){
		//so conneccoes podem ser removidas
		if (!(l instanceof Connection)) return;
		//procura o fim da cadeia
		while(l.to != null)			
			l = step(l, l.to);
		Node l2;
		while(l.from!=null){
			l2 = step(l, l.from);
			l.reset();
			l = l2;
		}
		l.reset();
	}
	//remove parte de uma ligacao, recebendo uma ligacao e a direcao que se pretende remover
	public void removeConnection(Node l, Direction d){
		if((l.to != null || l.from != d)) return;
		if( l.isInvalid() )return;
		Node l2 = step(l, d);
		l2.to = null;
		l.reset();
	}
	//inicializa um tabuleiro novo
 	public void init(int cx, int cy){
	 	max_x_size = cy;
	 	max_y_size = cx;
		board = new Node[max_y_size][max_x_size];
		for(int y = 0;y<max_y_size;y++)
			for(int x = 0;x<max_x_size;x++)
				board[y][x] = new Node(y,x);
	}
	//verifica se o nivel foi completo
	public boolean checkEnd(){
		for(int x = 0;x<max_x_size; x++)
			for(int y = 0;y<max_y_size; y++)
				if (!board[y][x].isInvalid()) return false;
		return true;
	}
	// Verifica se uma conecao foi completada
	public boolean isLocked(Node n1){

		Node n2,n3,n4;
		n4 = n1;
		while(n1.from != null){
			if( n1.to == null )return false;
			n1 = board[n1.y + n1.from.dy][n1.x + n1.from.dx];
		}
		n3 = n1;
		while(n4.to != null ){
			if( n4.from == null)return false;
			n4 = board[n4.y + n4.to.dy][n4.x + n4.to.dx];
		}
		n2 = n4;

		return n3.isInvalid() && n2.isInvalid();
		
	}
	
	public boolean validColors(Node l1,Node l2){
		if( (l1.color == l2.color) && l1.color != noColor)
			 return true;
		else if( l2.color == noColor && l1.color != noColor)
			return true;
		else 
			return false;
	}
	
	private void changeDirections(Node n){
		Direction old;
		do{
			if( !(n instanceof Connection) ){
				if( n.to == null){
					n.to = n.from;
					n.from = null;
				}else {
					n.from = n.to;
					n.to = null;
					}
					break;
				}						
			old = n.to;
			n.to = n.from;
			n.from = old;
			n = board[n.y + n.to.dy][n.x + n.to.dx];
		
		} while(n.to!=null || n.from!=null);
	}
	
	private boolean directionChanged(Node n1, Node n2){
		return (n1.to == null && n2.to == null && n1.from != null && n2.from != null) || (n1.from == null && n2.from == null && n1.to != null && n2.to != null);
	}
	
	
}
