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

public class AttackCommand
{
    public static enum CommandInput {UP,  DOWN, LEFT, RIGHT}
    public CommandInput[] commandStringList;
    public CommandInput[] notDoneList;
    public CommandInput[] doneList;

    public AttackCommand(String[] inList)
    {
        this.commandStringList = new CommandInput[10];
        this.notDoneList = new CommandInput[10];
        this.doneList = new CommandInput[10];

        for (int i = inList.length -1; i > -1;i--)
        {
            switch(inList[i])
            {
                case "up":
                    commandStringList[i] = CommandInput.UP;
                    break;
                case "down":
                    commandStringList[i] = CommandInput.DOWN;
                    break;
                case "right":
                    commandStringList[i] = CommandInput.RIGHT;
                    break;
                case "left":
                    commandStringList[i] = CommandInput.LEFT;
                    break;

            }
        }
        this.doneList = this.commandStringList;
    }

    public void enterCommand(String commandIn)
    {
        CommandInput currentInput;
        switch(commandIn)
        {
            case "up":
                currentInput = CommandInput.UP;
                break;
            case "down":
                currentInput = CommandInput.DOWN;
                break;
            case "right":
                currentInput = CommandInput.RIGHT;
                break;
            case "left":
                currentInput = CommandInput.LEFT;
                break;

        }




    }



}
