package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentMap;

public class AttackCommand implements Disposable
{
    @Override
    public void dispose()
    {

    }

    public static enum CommandInput {UP,  DOWN, LEFT, RIGHT, ERROR}
    public ArrayList<CommandInput> commandStringList;
    public ArrayList<CommandInput> notDoneList;
    public ArrayList<CommandInput> doneList;

    public AttackCommand(String[] inList)
    {
        this.commandStringList = new ArrayList<CommandInput>();
        this.notDoneList = new ArrayList<CommandInput>();
        this.doneList = new ArrayList<CommandInput>();

        for (int i = 0; i < inList.length ;i++)
        {
            this.commandStringList.add(i,this.strConvert(inList[i]));
            this.notDoneList.add(i,this.strConvert(inList[i]));
        }
    }

    public boolean enterCommand(String commandIn)
    {

            CommandInput currentInput = this.strConvert(commandIn);
            if(currentInput == notDoneList.get(0))
            {
                //Gdx.app.log("INPUT", this.commandConvert(this.notDoneList.get(0)));
                CommandInput commandDone = this.notDoneList.remove(0);
                this.doneList.add(commandDone);
            }
            else
            {
                this.doneList.clear();
                this.notDoneList.clear();
                this.notDoneList.addAll(this.commandStringList);
            }

        if(!this.notDoneList.isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    private CommandInput strConvert (String strIn)
    {
        switch (strIn)
        {
            case "up":
                return CommandInput.UP;
            case "down":
                return CommandInput.DOWN;
            case "right":
                return CommandInput.RIGHT;
            case "left":
                return CommandInput.LEFT;
        }
        return CommandInput.ERROR;
    }

    private String commandConvert (CommandInput comIn)
    {
        switch (comIn)
        {
            case UP:
                return "^";
            case DOWN:
                return "v";
            case RIGHT:
                return ">";
            case LEFT:
                return "<";
        }
        return "Error";
    }


    public void displayLists()
    {
        String commandStr = "";
        for (int i = 0; i < this.commandStringList.size() ;i++)
        {
            commandStr = commandStr + "" + this.commandConvert(commandStringList.get(i));
        }
        Gdx.app.log("ATTACK COMMAND", commandStr);

        String commandStr2 = "";
        for (int i = 0; i < this.doneList.size() ;i++)
        {
            commandStr2 = commandStr2 + "" + this.commandConvert(doneList.get(i));
        }

        Gdx.app.log("DONE COMMAND", commandStr2);

        String commandStr3 = " ";
        for (int i = 0; i < this.notDoneList.size() ;i++)
        {
            commandStr3 = commandStr3 + "" + this.commandConvert(notDoneList.get(i));
        }

        Gdx.app.log("NOT DONE COMMAND", commandStr3);
    }

}
