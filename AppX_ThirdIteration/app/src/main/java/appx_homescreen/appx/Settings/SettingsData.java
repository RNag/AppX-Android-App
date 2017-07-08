package appx_homescreen.appx.Settings;

public class SettingsData {
    private int _id;

    private String _userName;
    private String _userOrg;

    private float _userRating;
    private int _numEntries;


    public SettingsData(String userName, int _numEntries) {
        this._userName = userName;
        this._userOrg = "N/A";
        this._userRating = 0;
        this._numEntries = _numEntries;
    }

    public SettingsData(String userName, String _userOrg, float _userRating) {
        this._userName = userName;
        this._userOrg = _userOrg;
        this._userRating = _userRating;
        this._numEntries = 0;
    }

    public SettingsData(String userName, String _userOrg, float _userRating, int _numEntries) {
        this._userName = userName;
        this._userOrg = _userOrg;
        this._userRating = _userRating;
        this._numEntries = _numEntries;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_userName() {
        return _userName;
    }

    public void set_userName(String _userName) {
        this._userName = _userName;
    }

    public String get_userOrg() {
        return _userOrg;
    }

    public void set_userOrg(String _userOrg) {
        this._userOrg = _userOrg;
    }

    public float get_userRating() {
        return _userRating;
    }

    public void set_userRating(float _userRating) {
        this._userRating = _userRating;
    }

    public int get_numEntries() {
        return _numEntries;
    }

    public void set_numEntries(int _numEntries) {
        this._numEntries = _numEntries;
    }
}

