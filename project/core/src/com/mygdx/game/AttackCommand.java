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

public class AttackCommand
{
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
            this.commandStringList.set(i,this.strConvert(inList[i]));
        }
        this.doneList = this.commandStringList;
    }

    public void enterCommand(String commandIn)
    {
        CommandInput currentInput = this.strConvert(commandIn);
        if(currentInput == notDoneList.get(0))
        {
            this.doneList.add(0,notDoneList.remove(0));
        }
        else
        {
            this.doneList.clear();
            this.notDoneList = this.commandStringList;
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
}
