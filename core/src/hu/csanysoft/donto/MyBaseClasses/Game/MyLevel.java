package hu.csanysoft.donto.MyBaseClasses.Game;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

import hu.csanysoft.donto.MyBaseClasses.Scene2D.MyActor;
import hu.csanysoft.donto.MyBaseClasses.Scene2D.MyStage;
import hu.csanysoft.donto.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

public class MyLevel {

    public final OneSpriteStaticActor backgroundActor;
    private ArrayList<MyActor> actors;
    public static int ALL = -1;
    private MyStage stage;
    private int ID;
    private boolean showingActors = false;

    public MyLevel(final Texture background, MyStage stage) {
        actors = new ArrayList<MyActor>();
        this.stage = stage;
        backgroundActor = new OneSpriteStaticActor(background) {
            @Override
            public void act(float delta) {
                super.act(delta);
            }

            @Override
            public void init() {
                super.init();
            }
        };
        actors.add(backgroundActor);
        stage.addActor(backgroundActor);
        backgroundActor.setVisible(false);
    }

    @Getter
    public int getID() {
        return ID;
    }

    @Setter
    public void setID(int ID) {
        this.ID = ID;
    }

    @Getter
    public ArrayList<MyActor> getActors() {
        return actors;
    }

    public void removeActorFromLevel(MyActor actor, boolean all) {
        try {
            if (!all){
                if(!isActorOnLevel(actor)) throw new ActorIsNotOnLevelException("Ilyen actor nincs a pályán!");
                actors.remove(actor);
                actor.remove();
            }
            else for (MyActor a : actors) if(a.getClass() == actor.getClass()) {
                if(!isActorOnLevel(actor)) throw new ActorIsNotOnLevelException("Ilyen actor nincs a pályán!");
                actors.remove(a);
                actor.remove();
            }
        } catch (ActorIsNotOnLevelException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Nincs ilyen actor a pályán! NullPointer" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeActorFromLevel(MyActor actor) {
        try {
            if(!isActorOnLevel(actor)) throw new ActorIsNotOnLevelException("Ilyen actor nincs a pályán!");
            actors.remove(actor);
            actor.remove();
        } catch (ActorIsNotOnLevelException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Nincs ilyen actor a pályán! NullPointer" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeActorFromLevel(MyActor... actorGroup) {
        try{
            for (MyActor actor : actorGroup) {
                if(!isActorOnLevel(actor)) throw new ActorIsNotOnLevelException("Ilyen actor nincs a pályán!");
                actor.remove();
            }
        } catch (ActorIsNotOnLevelException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Nincs ilyen actor a pályán! NullPointer" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeActorFromLevel(int i) {
        try {
            if(i > -1 && i < actors.size()) {
                if(!isActorOnLevel(actors.get(i))) throw new ActorIsNotOnLevelException("Ilyen ID-vel nincs actor a pályán!");
                actors.get(i).remove();
                actors.remove(i);
            }
            else if (i == -1) {
                for (MyActor actor : actors) {
                    actor.remove();
                }
                actors.clear();
            }
        } catch (ActorIsNotOnLevelException ex) {
            ex.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Nincs ilyen ID! NullPointer" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addActorToLevel(MyActor... actorGroup) {
        for (MyActor actor : actorGroup) {
            stage.addActor(actor);
            actors.add(actor);
            actor.setVisible(false);
        }
    }

    public void deleteLevel() {
        for (MyActor a : actors) {
            stage.getActors().removeValue(a, true);
        }
        actors.clear();
        ID = -1;
    }

    public void showActors(boolean show) {
        showingActors = show;
        for (MyActor a : actors) {
            a.setVisible(show);
        }
    }

    public boolean isShowingActors() {
        return showingActors;
    }

    public boolean isActorOnLevel(MyActor actor) {
        for (MyActor a : actors) if(a.equals(actor)) return true;
        return false;
    }
    public class ActorIsNotOnLevelException extends Exception {
        public ActorIsNotOnLevelException() {
            super();
        }

        public ActorIsNotOnLevelException(String message) {
            super(message);
        }

        public ActorIsNotOnLevelException(String message, Throwable cause) {
            super(message, cause);
        }

        public ActorIsNotOnLevelException(Throwable cause) {
            super(cause);
        }
    }
}
