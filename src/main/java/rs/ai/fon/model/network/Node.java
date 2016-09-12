package rs.ai.fon.model.network;

public class Node {

	private int id;
	private String label;

	public Node(int id, String label) {
		this.id = id;
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", label=" + label + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node) {
			Node node = (Node) obj;
			if (this.label.equals(node.getLabel())) {
				System.out.println(this + "/t" + node);
				return true;
			}
		}
		return false;
	}

}
