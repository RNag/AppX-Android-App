package appx_homescreen.appx;

public class EventData {
    private int _id;
    private String _Who;
    private String _What;
    private String _When;
    private String _Where;
    private String _How;
    private int _More;

    public EventData(String _Who, String _What, String _When, String _Where, String _How, int _More){
        this._Who = _Who;
        this._What = _What;
        this._When = _When;
        this._Where = _Where;
        this._How = _How;
        this._More = _More;
    }

    public EventData(String _Who, String _What, String _When, String _Where, String _How){
        this._Who = _Who;
        this._What = _What;
        this._When = _When;
        this._Where = _Where;
        this._How = _How;
        this._More = 0;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_Who() {
        return _Who;
    }

    public void set_Who(String _Who) {
        this._Who = _Who;
    }

    public String get_What() {
        return _What;
    }

    public void set_What(String _What) {
        this._What = _What;
    }

    public String get_When() {
        return _When;
    }

    public void set_When(String _When) {
        this._When = _When;
    }

    public String get_Where() {
        return _Where;
    }

    public void set_Where(String _Where) {
        this._Where = _Where;
    }

    public String get_How() {
        return _How;
    }

    public void set_How(String _How) {
        this._How = _How;
    }

    public int get_More() {
        return _More;
    }

    public void set_More(int _More) {
        this._More = _More;
    }
}