
public class RobddNode {
	private final int nodeNumber;
	private int level;
	private int variable;
	
	// Links to the child RobddNodes.
	private int leftLink;
	private int rightLink;
	private RobddNode leftChild;
	private RobddNode rightChild;
	
	private int x;
	private int y;
	private int count;
	/*
	 * @param nodeNum: The unique number of the node.
	 * @param variable: The variable the node holds.
	 * @param level: The level of the node, determined by the variable (user should check the varible 
	 * ordering and assign the value based on that.)
	 */
	public RobddNode(int i, int l, int h, int nodeNumber, RobddNode left, RobddNode right) {
		this.nodeNumber = nodeNumber;
		this.variable = i;
		this.count = 0;
		this.level = -1;
		this.x = -1;
		this.y = -1;
		
		this.leftLink = l;
		this.rightLink = h;
		this.leftChild = left;
		this.rightChild = right;
	}
	
	/** Increases number of times node has been counted.
	 * @return the number of times the node has been counted.
	 */
	 public void incCount() {
		 this.count++;
	 }
	
	public int getCount() {
		return this.count;
	}

	public int getLevel() {
		return this.level;
	}
	
	public int getLeftLink() {
		return this.leftLink;
	}

	public int getNodeNum() {
		return this.nodeNumber;
	}

	public int getRightLink() {
		return this.rightLink;
	}
	
	public RobddNode getLeftChild() {
		return this.leftChild;
	}
	
	public RobddNode getRightChild() {
		return this.rightChild;
	}

	public int getVar() {
		return this.variable;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public void setLeftChild(RobddNode l) {
		this.leftChild = l;
	}
	
	public void setRightChild(RobddNode r) {
		this.rightChild = r;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}