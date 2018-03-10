package com.sandbox.game;

/**
 * Created by zliu on 2018-02-16.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class Asset {

    //TILES
    public static Texture grass_01, grass_02, grass_03, grass_04;
    public static Texture grass_left, grass_right, grass_most_left, grass_most_right;
    public static Texture grass_left_upper_edge, grass_right_upper_edge, grass_inside_left, grass_inside_right;
    public static Texture grass_top, grass_top_right, grass_top_left;
    public static Texture grass_inlet, grass_hole;
    public static Texture water_01, water_02, water_03, water_04;
    public static Texture cliff;
    public static Texture cliff_bottom;

    //STUFF
    public static Texture tree;
    public static Texture house;

    //Player
    public static Texture player_front, player_back, player_left, player_right;

    public static void Load()
    {
        grass_01 = new Texture("sprites/grass/grass_01.png");
        grass_02 = new Texture("sprites/grass/grass_02.png");
        grass_03 = new Texture("sprites/grass/grass_03.png");
        grass_04 = new Texture("sprites/grass/grass_04.png");

        grass_left = new Texture("sprites/grass/right_grass_edge.png");
        grass_right = new Texture("sprites/grass/left_grass_edge.png");
        grass_most_left = new Texture("sprites/grass/right_most_grass_edge.png");
        grass_most_right = new Texture("sprites/grass/left_most_grass_edge.png");

        grass_left_upper_edge = new Texture("sprites/grass/left_upper_edge.png");
        grass_right_upper_edge = new Texture("sprites/grass/right_upper_edge.png");
        grass_inside_left = new Texture("sprites/grass/grass_inside_left.png");
        grass_inside_right = new Texture("sprites/grass/grass_inside_right.png");

        grass_top = new Texture("sprites/grass/top.png");
        grass_top_right = new Texture("sprites/grass/top_right.png");
        grass_top_left = new Texture("sprites/grass/top_left.png");

        grass_inlet = new Texture("sprites/grass/grass_inlet.png");
        grass_hole = new Texture("sprites/grass/grass_hole.png");

        water_01 = new Texture("sprites/water/water_01.png");
        water_02 = new Texture("sprites/water/water_02.png");
        water_03 = new Texture("sprites/water/water_03.png");
        water_04 = new Texture("sprites/water/water_04.png");
        cliff = new Texture(Gdx.files.internal("sprites/cliff.png"));
        cliff_bottom = new Texture(Gdx.files.internal("sprites/cliff_bottom.png"));

        tree = new Texture("sprites/entities/tree.png");
        house = new Texture("sprites/entities/house.png");
        player_front = new Texture("sprites/entities/player/player_front.png");
        player_back = new Texture("sprites/entities/player/player_back.png");
        player_left = new Texture("sprites/entities/player/player_left.png");
        player_right = new Texture("sprites/entities/player/player_right.png");

    }

    public void dispose()
    {
        grass_01.dispose();
        grass_02.dispose();
        grass_03.dispose();
        grass_04.dispose();
        grass_left.dispose();
        grass_right.dispose();
        grass_left_upper_edge.dispose();
        grass_right_upper_edge.dispose();
        grass_top.dispose();
        grass_top_right.dispose();
        grass_top_left.dispose();
        water_01.dispose();
        water_02.dispose();
        water_03.dispose();
        water_04.dispose();
        cliff.dispose();
        tree.dispose();
        player_front.dispose();
        player_back.dispose();
        player_left.dispose();
        player_right.dispose();
    }

    public static Texture GetRandomGrassTexture()
    {
        Texture grass;

        int tile = MathUtils.random(20);
        switch (tile) {
            case 1:  grass = grass_01;
                break;
            case 2:  grass = grass_02;
                break;
            case 3:  grass = grass_03;
                break;
            case 4:  grass = grass_04;
                break;
            default: grass = grass_01;
                break;
        }

        return grass;
    }

    public static Texture GetRandomWaterTexture()
    {
        Texture water;

        int tile = MathUtils.random(20);
        switch (tile) {
            case 1:  water = water_01;
                break;
            case 2:  water = water_02;
                break;
            case 3:  water = water_03;
                break;
            case 4:  water = water_04;
                break;
            default: water = water_01;
                break;
        }

        return water;
    }
}
