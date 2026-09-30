package it.gruppoinit.visualizer;

public class Key {
	
	private String _id;
	private String _for;
	private String _name;
	private String _type;
	private String _default;
	
	public Key(String _id, String _for, String _name, String _type, String _default){
		this._id = _id;
		this._for = _for;
		this._name = _name;
		this._type = _type;
		this._default = _default;
	}
	
	public String get_id() {
		return _id;
	}
	public String get_for() {
		return _for;
	}
	public String get_name() {
		return _name;
	}
	public String get_type() {
		return _type;
	}
	public String get_default() {
		return _default;
	}
	
	
	
	
}
