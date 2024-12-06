package bot.telegram;

public class TelegramButtons {
    private String[] buttons;
    private boolean buttonsDefined = false;

    public void defineButtons(String... buttons) {
        this.buttons = new String[buttons.length];
        System.arraycopy(buttons, 0, this.buttons, 0, buttons.length);
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
