package appx_homescreen.appx;

public class ListData {
    private int _id;
    private String _listTitle;
    private String _listOrg;
    private String _listDate;
    private String _list_endDate;
    private String _list_fromTime;
    private String _list_toTime;
    private String _listWhere;
    private String _list_Address;
    private String _list_cityName;
    private String _list_State;
    private String _listAbout;
    private String _listAuthor;

    //constructor for no-frills event description
    public ListData(String _listTitle, String _listOrg, String _listDate, String _listAbout, String _listAuthor){
        this._listTitle = _listTitle;
        this._listOrg = _listOrg;
        this._listDate = _listDate;
        this._list_endDate = "";
        this._list_fromTime = "";
        this._list_toTime = "";
        this._listWhere = "";
        this._list_Address = "";
        this._list_cityName = "";
        this._list_State = "";
        this._listAbout = _listAbout;
        this._listAuthor = _listAuthor;
    }

    //constructor for fetching entry from database
    public ListData(int _id, String _listTitle, String _listOrg, String _listDate, String _list_endDate, String _list_fromTime, String _list_toTime, String _listWhere, String _list_Address, String _list_cityName, String _list_State, String _listAbout, String _listAuthor){
        this._id = _id;
        this._listTitle = _listTitle;
        this._listOrg = _listOrg;
        this._listDate = _listDate;
        this._list_endDate = _list_endDate;
        this._list_fromTime = _list_fromTime;
        this._list_toTime = _list_toTime;
        this._listWhere = _listWhere;
        this._list_Address = _list_Address;
        this._list_cityName = _list_cityName;
        this._list_State = _list_State;
        this._listAbout = _listAbout;
        this._listAuthor = _listAuthor;
    }

    //constructor for adding an event with the simplified details
    public ListData(String _listTitle, String _listOrg, String _listDate, String _listWhere, String _listAbout, String _listAuthor){
        this._listTitle = _listTitle;
        this._listOrg = _listOrg;
        this._listDate = _listDate;
        this._list_endDate = "";
        this._list_fromTime = "";
        this._list_toTime = "";
        this._listWhere = _listWhere;
        this._list_Address = "";
        this._list_cityName = "";
        this._list_State = "";
        this._listAbout = _listAbout;
        this._listAuthor = _listAuthor;
    }

    //constructor for adding an event with additional details
    public ListData(String _listTitle, String _listOrg, String _listDate,  String _list_endDate, String _list_fromTime, String _list_toTime, String _listWhere, String _list_Address, String _list_cityName, String _list_State, String _listAbout, String _listAuthor){
        this._listTitle = _listTitle;
        this._listOrg = _listOrg;
        this._listDate = _listDate;
        this._list_endDate = _list_endDate;
        this._list_fromTime = _list_fromTime;
        this._list_toTime = _list_toTime;
        this._listWhere = _listWhere;
        this._list_Address = _list_Address;
        this._list_cityName = _list_cityName;
        this._list_State = _list_State;
        this._listAbout = _listAbout;
        this._listAuthor = _listAuthor;
    }

    /** Setters */
    public void set_id(int _id) { this._id = _id; }
    public void set_listTitle(String _listTitle) { this._listTitle = _listTitle; }
    public void set_listOrg(String _listOrg) { this._listOrg = _listOrg; }
    public void set_listDate(String _listDate) {this._listDate = _listDate;}
    public void set_list_endDate(String _list_endDate) {this._list_endDate = _list_endDate;}
    public void set_list_fromTime(String _list_fromTime) { this._list_fromTime = _list_fromTime; }
    public void set_list_toTime(String _list_toTime) { this._list_toTime = _list_toTime; }
    public void set_listWhere(String _listWhere) { this._listWhere = _listWhere; }
    public void set_list_Address(String _list_Address) { this._list_Address = _list_Address; }
    public void set_list_cityName(String _list_cityName) { this._list_cityName = _list_cityName; }
    public void set_list_State(String _list_State) { this._list_State = _list_State; }
    public void set_listAbout(String _listAbout) { this._listAbout = _listAbout; }
    public void set_listAuthor(String _listAuthor) { this._listAuthor = _listAuthor; }

    /** Getters */
    public int get_id() {
        return _id;
    }
    public String get_listTitle() { return _listTitle; }
    public String get_listOrg() { return _listOrg; }
    public String get_listDate() { return _listDate; }
    public String get_list_endDate() { return _list_endDate; }
    public String get_list_fromTime() { return _list_fromTime; }
    public String get_list_toTime() { return _list_toTime; }
    public String get_listWhere() { return _listWhere; }
    public String get_list_Address() { return _list_Address; }
    public String get_list_cityName() { return _list_cityName; }
    public String get_list_State() { return _list_State; }
    public String get_listAbout() { return _listAbout; }
    public String get_listAuthor() { return _listAuthor; }
}