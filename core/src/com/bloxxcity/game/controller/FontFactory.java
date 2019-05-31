package com.bloxxcity.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.Locale;

public class FontFactory {
    private static final String RUSSIAN_FONT_NAME = "fonts/comic.ttf";
    private static final String RUSSIAN_CHARACTERS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
            + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
            + "1234567890.,:;_¡!¿?\"'+-*/()[]={}";

    private BitmapFont ruFont;

    // Singleton: unique instance
    private FontFactory instance;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    /**
     * Private constructor for singleton pattern
     */
    private FontFactory() {
        super();
    }

    private BitmapFont generateFont(String fontName, String characters, int size, Color color) {

        // Configure font parameters
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = characters;
        parameter.size = size;
        parameter.color = color;
        // Generate font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontName));
        BitmapFont font = generator.generateFont(parameter);

        // Dispose resources
        generator.dispose();

        return font;
    }

    public FontFactory(int size, Color color) {
        ruFont = generateFont(RUSSIAN_FONT_NAME, RUSSIAN_CHARACTERS, size, color);
    }

    public BitmapFont getFont(Locale locale) {
        if ("ru".equals(locale.getLanguage())) {
            return ruFont;
        } else {
            throw new IllegalArgumentException("Not supported language");
        }
    }


}
