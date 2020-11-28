package Gateways;

public class TestMain {
    public static void main(String[] args) {
        try{
            UserGateway ug = new UserGateway("phase2/DataBase/test.ini");
            ug.writeData();
            ug.loadData();
        }catch (Exception e){
            System.out.println("Something is wrong!");
        }

    }
}
