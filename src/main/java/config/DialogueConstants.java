package config;

import com.littleBeasts.Program;
import de.gurkenlabs.litiengine.gui.SpeechBubbleAppearance;
import org.json.JSONObject;
import utilities.JsonReader;

import java.awt.*;

public class DialogueConstants {

    public static final Color foreColor = new Color(255, 255, 255, 255);
    public static final Color backColor = new Color(0, 0, 0, 255);
    private static final Color borderColor = new Color(255, 0, 0, 255);
    private static final float padding = 3.f;

    public static final JSONObject dialogueTree = JsonReader.readJson(Program.class.getResource("/JSON/dialogueTree.JSON").getPath());
    public static final SpeechBubbleAppearance defaultSpeechBubble = new SpeechBubbleAppearance(foreColor, backColor, borderColor, padding);
}