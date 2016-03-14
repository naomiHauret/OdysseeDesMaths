package com.odysseedesmaths.util;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class DisplayTextAction extends TemporalAction {

    private String text;
    private boolean finished;

    @Override
    protected void update(float percent) {
        ((Label)actor).setText(text.substring(0, Math.round(text.length() * percent)));
    }

    @Override
    protected void begin() {
        super.begin();
        finished = false;
    }

    @Override
    protected void end() {
        super.end();
        finished = true;
    }

    @Override
    public void reset() {
        super.reset();
        text = "";
    }

    public String getText() {
        return text;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setText(String text) {
        this.text = text;
    }
}
