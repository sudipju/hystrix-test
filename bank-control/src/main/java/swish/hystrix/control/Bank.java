package swish.hystrix.control;

public class Bank {
	private final static int BASEPORT = 8090;
	private int id;
	private String name;
	
	public Bank() {
		
	}
	public Bank(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPort() {
		return BASEPORT + id;
	}
	public String getCircuitBreakerName() {
		return name;
	}
}
