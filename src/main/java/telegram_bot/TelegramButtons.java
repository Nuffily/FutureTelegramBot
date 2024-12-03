package telegram_bot;

import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class TelegramButtons {
    private String[] buttons;
    private boolean buttonsDefined = false;

    public void defineButtons(String... buttons) {
        this.buttons = new String[buttons.length];
        for (int i = 0; i < buttons.length; i++)
            this.buttons[i] = buttons[i];
        buttonsDefined = true;
    }

    public String[] getButtons() {
        buttonsDefined = false;
        return buttons;
    }

    public boolean isThereButtons() {
        return buttonsDefined;
    }
}
