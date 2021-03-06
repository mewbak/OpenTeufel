package org.openteufel.game.entities.player;

import java.io.IOException;

import org.openteufel.game.Entity;
import org.openteufel.game.WorldCallback;
import org.openteufel.game.entities.AnimatedEntity;
import org.openteufel.game.entities.WalkingEntity;
import org.openteufel.game.utils.Position2d;
import org.openteufel.ui.ImageLoader;
import org.openteufel.ui.Renderer;

// e.g.: plrgfx\\rogue\\rhb\\rhbwl.cl2

//    Character 1
//    r rogue
//    s sorceror
//    w warrior

//    Character 2
//    h heavy armor
//    m
//    l

//    Character 3
//    a axe
//    b bow
//    d swort + shield
//    h mace + shield
//    m mace
//    n nothing
//    s sword
//    t staff
//    u shield

//    Character 4+5
//    as standing (ready for attack)
//    at attack
//    aw walking (ready for attack)
//
//    wl town walking
//    st town standing
//
//    ht getting hit
//
//    fm fire magic
//    qm holy magic
//    lm lightning magic
public class PlayerEntity extends WalkingEntity
{
    public static final int CLASS_WARRIOR  = 0;
    public static final int CLASS_SORCEROR = 1;
    public static final int CLASS_ROGUE    = 2;

    private final int       playerclass;
    private final boolean   isTown;
    private final String    playerclassinitial;
    private final String    celBasePath;

    public PlayerEntity(final Position2d pos, final int playerclass, final boolean isTown)
    {
        super(pos, 5, TEAM_GOOD);
        this.playerclass = playerclass;
        this.isTown = isTown;

        String playerclassname;
        switch (playerclass)
        {
            case CLASS_WARRIOR:
                playerclassname = "warrior";
                this.playerclassinitial = "w";
                break;
            case CLASS_SORCEROR:
                playerclassname = "sorceror";
                this.playerclassinitial = "s";
                break;
            case CLASS_ROGUE:
                playerclassname = "rogue";
                this.playerclassinitial = "r";
                break;

            default:
                throw new IllegalArgumentException();
        }
        this.celBasePath = "plrgfx\\" + playerclassname + "\\" + this.playerclassinitial + "ha" + "\\" + this.playerclassinitial + "ha";
        this.updateAnimation(ANIM_STANDING);
    }

    @Override
    protected int getBottomOffset()
    {
        return 16;
    }

    @Override
    protected Entity[] getAdditionalPreloadEntitites()
    {
        return null;
    }

    @Override
    protected void performWalk(int gametime, int currentFrameId, WorldCallback world)
    {
    }

    @Override
    protected void finishWalk(int gametime, int currentFrameId, WorldCallback world)
    {
    }

    @Override
    protected void performAttack(final int gametime, WorldCallback world, Entity targetEntity)
    {

    }

    @Override
    protected int[] getAnimTypes()
    {
        if (this.isTown)
        {
            return new int[] { ANIM_STANDING, ANIM_WALKING, };
        }
        else
        {
            return new int[] { ANIM_STANDING, ANIM_WALKING, ANIM_ATTACKING, ANIM_HIT };
        }
    }

    @Override
    protected String getCelPathStand()
    {
        final StringBuilder ret = new StringBuilder(this.celBasePath);
        if (this.isTown)
            ret.append("st");
        else
            ret.append("as");
        ret.append(".cl2");
        return ret.toString();
    }

    @Override
    protected String getCelPathWalk()
    {
        final StringBuilder ret = new StringBuilder(this.celBasePath);
        if (this.isTown)
            ret.append("wl");
        else
            ret.append("aw");
        ret.append(".cl2");
        return ret.toString();
    }

    @Override
    protected String getCelPathAttack()
    {
        return null;
    }

    @Override
    protected String getCelPathHit()
    {
        return null;
    }

    @Override
    protected String getCelPathDeath()
    {
        return null;
    }

    @Override
    protected int getNumFramesStand()
    {
        if (this.isTown)
            return 160 / 8; // st
        else
            return 64 / 8; // as
    }

    @Override
    protected int getNumFramesWalk()
    {
        if (this.isTown)
            return 64 / 8; // wl
        else
            return 64 / 8; // aw
    }

    @Override
    protected int getNumFramesAttack()
    {
        return 0;
    }

    @Override
    protected int getNumFramesHit()
    {
        return 0;
    }

    @Override
    protected int getNumFramesDeath()
    {
        return 0;
    }

    @Override
    protected int getFrameAttack()
    {
        return 0;
    }

}
