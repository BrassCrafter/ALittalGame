import GLOOP.*;

public class Game {

    //Variables
        double fieldMinX = -300, fieldMaxX = 300, fieldMinY = -500, fieldMaxY = 500;
        double playerVelocity = 2;
        boolean startFall = true;
        double bulletRandNum;
        int xFactor = 1, yFactor = -1;
        double xBulletVelocity = 0.5, yBulletVelocity = 0.5;
        boolean maxTarget = false;
        int targetAmount = 0;
        double xTargetPos = -290, yTargetPos = 490;

    //\Variables


    GLKamera cam1;
    GLLicht light;
    GLHimmel sky;
    GLTastatur kb;
    GLQuader field, player;
    GLKugel bullet;
    GLKugel [] targetArray;

    public void start(){

        light = new GLLicht();
        sky = new GLHimmel("src/AirHockeySky.png");
        kb = new GLTastatur();

        bullet = new GLKugel(0, 0, 15, 15);
        bullet.setzeFarbe(0, 1, 0);

        player = new GLQuader(0, 0, 15, 60, 5, 10);
        player.setzePosition(0, -400, 15);
        player.setzeFarbe(1, 1, 1);

        field = new GLQuader(0, 0, 0, 600, 1000, 10, "src/background.png");
        field.rotiere(180, 0, 0, 1, 0, 0, 0);


        cam1 = new GLKamera(1280, 720);
        cam1.setzePosition(0, 0, 1000);
        //cam1.aus();
        targetSpawner();

    }

    public void game(){

        while(!kb.esc()){
            System.out.print(" ");
            player();
            bullet();
            Sys.warte(1);
        }
        Sys.beenden();
    }

    public void bullet(){

        if(startFall){
            bulletRandNum = Math.random();
            if(bulletRandNum>=0.5){
                xFactor = 1;
            }
            if(bulletRandNum<=0.5){
                xFactor = -1;
            }
            startFall = false;
        }

        if((bullet.gibX() <= player.gibX()+30 && bullet.gibY()-15 == player.gibY()) && (bullet.gibX() >= player.gibX()-30 && bullet.gibY()-15 == player.gibY())){
            yFactor = yFactor*-1;
        }

        if(bullet.gibX()-15>=fieldMinX){
            xFactor = xFactor*-1;
        }
        if(bullet.gibX()+15<=fieldMaxX){
            xFactor = xFactor*-1;
        }
        if(bullet.gibY()+15>=fieldMaxY){
            yFactor = yFactor*-1;
        }


        bullet.verschiebe(xBulletVelocity*xFactor, yBulletVelocity*yFactor, 0);

    }

    public void player(){
        //Movement
        if(kb.links()){
            player.verschiebe(-playerVelocity, 0, 0);
        }
        if(kb.rechts()){
            player.verschiebe(playerVelocity, 0, 0);
        }

        //Border
        if(player.gibX()-30<=-300){
            player.verschiebe(playerVelocity, 0, 0);
        }
        if(player.gibX()+30>=300){
            player.verschiebe(-playerVelocity, 0, 0);
        }

    }

    public void targetSpawner(){
        while(!maxTarget){
            if(targetArray != null || targetArray[targetAmount].gibX()+30<=300 && targetArray[targetAmount].gibY()+10>=0){
                targetArray[targetAmount] = new GLKugel(xTargetPos, yTargetPos, 15, 10, "src/targetColor.png");
                targetAmount++;
                xTargetPos = xTargetPos + 10;
            }
            else{
                xTargetPos = 290;
                yTargetPos = yTargetPos - 20;
                targetAmount++;
            }
            System.out.println(1);
            Sys.warte(10);
        }
    }

}