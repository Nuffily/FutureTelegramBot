package bot.telegram;

public class TelegramButtons {
    private String[] buttons;

    public void set(String... buttons) {
        this.buttons = new String[buttons.length];
        System.arraycopy(buttons, 0, this.buttons, 0, buttons.length);
    }

    public String[] getButtons() {
        String[] result = new String[buttons.length];
        System.arraycopy(this.buttons, 0, result, 0, result.length);
        buttons = new String[0];
        return result;
    }
}
