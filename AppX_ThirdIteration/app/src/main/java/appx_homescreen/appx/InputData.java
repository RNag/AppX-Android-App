package appx_homescreen.appx;

public class InputData {
    private int _id;
    private String _userName;
    private String _userPass;
    private String _fullName;
    private String _Occupation;
    private String _Org;

    private int _prefix;
    private int _edLevel;
    private int _age;


    public InputData(String userName, String userPass, String fullName, String Occupation, String Org, int prefix, int edLevel){
        this._userName = userName;
        this._userPass = userPass;
        this._fullName = fullName;
        this._Occupation = Occupation;
        this._Org = Org;
        this._prefix = prefix;
        this._edLevel = edLevel;
    }

    public InputData(String userName, String userPass, String fullName, String Occupation, String Org, int prefix, int edLevel, int age){
        this._userName = userName;
        this._userPass = userPass;
        this._fullName = fullName;
        this._Occupation = Occupation;
        this._Org = Org;
        this._prefix = prefix;
        this._edLevel = edLevel;
        this._age = age;
    }

    /** Setters */
    public void set_id(int _id) {this._id = _id;}
    public void set_userName(String _userName) {
        this._userName = _userName;
    }
    public void set_userPass(String _userPass) {
        this._userPass = _userPass;
    }
    public void set_fullName(String _fullName) {this._fullName = _fullName;}
    public void set_Occupation(String _Occupation) {this._Occupation = _Occupation;}
    public void set_Org(String _Org) {this._Org = _Org;}
    public void set_prefix(int _prefix) {this._prefix = _prefix;}
    public void set_edLevel(int _edLevel) {this._edLevel = _edLevel;}
    public void set_age(int _age) {this._age = _age;}

    /** Getters */
    public int get_id() {
        return _id;
    }
    public String get_userName() {
        return _userName;
    }
    public String get_userPass() {
        return _userPass;
    }
    public String get_fullName() {
        return _fullName;
    }
    public String get_Occupation() {return _Occupation;}
    public String get_Org() {return _Org;}
    public int get_prefix() {return _prefix;}
    public int get_edLevel() {return _edLevel;}
    public int get_age() {return _age;}
}
