package  shared.transferObjects;

import java.util.ArrayList;

public class NewsflashList {
    private ArrayList<Newsflash> list;
    private int size;

    public NewsflashList(){
        list = new ArrayList<>();
        size = list.size();
    }

    public ArrayList<Newsflash> getAllNewsflashes(String sender){
        ArrayList<Newsflash> returnable = new ArrayList<>();
        for(int i  = 0; i < size; i++){
            if(list.get(i).getSender().equals(sender)){
                returnable.add(list.get(i));
            }
        }
        return returnable;
    }
    public ArrayList<Newsflash> getApprovedNewsflashes(String sender){
        ArrayList<Newsflash> returnable = new ArrayList<>();
        for(int i  = 0; i < size; i++){
            if(list.get(i).getSender().equals(sender) && list.get(i).getState().equals("Approved")){
                returnable.add(list.get(i));
            }
        }
        return returnable;
    }
    public ArrayList<Newsflash> getPendingNewsflashes(String sender){
        ArrayList<Newsflash> returnable = new ArrayList<>();
        for(int i  = 0; i < size; i++){
            if(list.get(i).getSender().equals(sender) && list.get(i).getState().equals("Pending")){
                returnable.add(list.get(i));
            }
        }
        return returnable;
    }
    public void addNewsflash(Newsflash newsflash){
        list.add(newsflash);
    }

    public int getSize() {
        return list.size();
    }

    public ArrayList<Newsflash> getList() {
        return list;
    }
}
