package com.example.login;

public class CheckIcon {
    public int picture;
    public int checkIconUser(int icon_id){
        switch (icon_id){
            case 0:
                picture = R.drawable.barash;
                break;
            case 1:
                picture = R.drawable.cucarecu;
                break;
            case 2:
                picture = R.drawable.ia;
                break;
            case 3:
                picture = R.drawable.korova;
                break;
            case 4:
                picture = R.drawable.shhhh;
                break;
            case 5:
                picture = R.drawable.meduzaaaaaa;
                break;
            case 6:
                picture = R.drawable.pavuk;
                break;
            case 7:
                picture = R.drawable.pchelka;
                break;
            case 8:
                picture = R.drawable.volchara;
                break;
            default:
                return R.drawable.ic_android;
        }
        return picture;
    }

    public int getPicture(){
        return picture;
    }
}
