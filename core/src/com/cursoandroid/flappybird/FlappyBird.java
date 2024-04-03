package com.cursoandroid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

//public class FlappyBird extends ApplicationAdapter {
//	private SpriteBatch batch;
//	private Texture[] passaros;
//	private Texture fundo;
//
//	// Configuration attributes
//	private int movimento;
//	private int larguraDispositivo;
//	private int alturaDispositivo;
//	private int variacao;
//
//	@Override
//	public void create() {
//		batch = new SpriteBatch();
//		passaros = new Texture[3];
//		passaros[0] = new Texture("passaro1.png");
//		passaros[1] = new Texture("passaro2.png");
//		passaros[2] = new Texture("passaro3.png");
//		fundo = new Texture("fundo.png");
//
//		larguraDispositivo = Gdx.graphics.getWidth();
//		alturaDispositivo = Gdx.graphics.getHeight();
//	}
//
//	@Override
//	public void render() {
//		variacao += Gdx.graphics.getDeltaTime();
//		Gdx.app.log("Variacao", "Variacao:" + Gdx.graphics.getDeltaTime());
//
//		if (variacao > 2) {
//			variacao = 0;
//		}
//
//		batch.begin();
//		batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
//		batch.draw(passaros[ (int) variacao ], 30, alturaDispositivo / 2);
//		batch.end();
//	}
//}

//In the updated code, I've changed the variacao increment to use
// Gdx.graphics.getDeltaTime() which provides a more consistent frame
// time-based movement.
// I've also added a log statement to monitor the variacao value.

//Note that I've added Gdx.gl.glClearColor(1, 1, 1, 1);
// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
// to the render method to clear the screen before each frame.
// This is necessary to avoid drawing the previous frame on top of the new one.



//public class FlappyBird extends ApplicationAdapter {
//	private SpriteBatch batch;
//	private Texture[] passaros;
//	private Texture fundo;
//
//	// Configuration attributes
//	private int movimento;
//	private int larguraDispositivo;
//	private int alturaDispositivo;
//	private float variacao = 0;
//	private float velocidadeQueda = 0;
//	private float posicaoInicialVertical;
//
//
//	@Override
//	public void create() {
//		batch = new SpriteBatch();
//		passaros = new Texture[3];
//		passaros[0] = new Texture("passaro1.png");
//		passaros[1] = new Texture("passaro2.png");
//		passaros[2] = new Texture("passaro3.png");
//		fundo = new Texture("fundo.png");
//
//		larguraDispositivo = Gdx.graphics.getWidth();
//		alturaDispositivo = Gdx.graphics.getHeight();
//		posicaoInicialVertical = alturaDispositivo / 2 ;
//	}
//
//	@Override
//	public void render() {
//		Gdx.gl.glClearColor(1, 1, 1, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//		variacao += Gdx.graphics.getDeltaTime() * 10 ;
//		velocidadeQueda++;
//		Gdx.app.log("Variacao", "Variacao:" + Gdx.graphics.getDeltaTime());
//
//		posicaoInicialVertical = posicaoInicialVertical - velocidadeQueda;
//
//		if (variacao > 2) {
//			variacao = 0;
//		}
//
//		batch.begin();
//		batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
//		batch.draw(passaros[ (int) variacao ], 30, alturaDispositivo / 2);
//		batch.end();
//	}
//}

//        I fixed the code by adding a maxVelocity
//        constant for the maximum falling
//        speed and limiting the velocidadeQueda within the render() method.
//
//        In this version, I added a MAX_VELOCITY constant for the maximum
//        falling speed. The velocidadeQueda is now limited to this value
//        within the render() method. I also changed the y position of
//        the bird to be posicaoInicialVertical in the batch.draw() method.



public class FlappyBird extends ApplicationAdapter {
    private static final float MAX_VELOCITY = 10;
    private SpriteBatch batch;
    private Texture[] passaros;
    private Texture fundo;
    private Texture canoBaixo;
    private Texture canoTopo;
    private Random numeroRandomico;
    private BitmapFont fonte;
    private Circle passaroCirculo;
    private Rectangle rectangleCanoTopo;
    private Rectangle rectangleCanoBaixo;
    private ShapeRenderer shape;



    // Configuration attributes
    private int movimento;
    private int larguraDispositivo;
    private int alturaDispositivo;
    private int estadoJogo=0; // 0 --> jogo nao iniciado  1 --> jogo iniciado
    private int pontuacao=0;


    private float variacao;
    private float velocidadeQueda;
    private float posicaoInicialVertical;
    private float posicaoMovimentoCanoHorizontal;
    private float espaçoEntreCanos;
    private float deltaTime;
    private float alturaEntreCanosRandomica;
    private  boolean marcouPonto=false;



    @Override
    public void create() {
        batch = new SpriteBatch();
        numeroRandomico = new Random();
        passaroCirculo = new Circle();
        rectangleCanoBaixo = new Rectangle();
        rectangleCanoTopo= new Rectangle();
        shape = new ShapeRenderer();
        fonte = new BitmapFont();
        fonte.setColor(Color.WHITE);
        fonte.getData().setScale(6);


        passaros = new Texture[3];
        passaros[0] = new Texture("passaro1.png");
        passaros[1] = new Texture("passaro2.png");
        passaros[2] = new Texture("passaro3.png");

        fundo = new Texture("fundo.png");
        canoBaixo = new Texture("cano_baixo.png");
        canoTopo = new Texture("cano_topo.png");


        larguraDispositivo = Gdx.graphics.getWidth();
        alturaDispositivo = Gdx.graphics.getHeight();
        posicaoInicialVertical = alturaDispositivo / 2;
        posicaoMovimentoCanoHorizontal = larguraDispositivo - 100;
        espaçoEntreCanos = 300;
    }

    @Override
    public void render() {

        deltaTime = Gdx.graphics.getDeltaTime();
        variacao += deltaTime * 10;

        if (variacao > 2) {
            variacao = 0;
        }

        if(estadoJogo == 0) {
            //nao iniciado

            if ( Gdx.input.justTouched()){
                estadoJogo = 1;
            }
        }
        else {

            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


            posicaoMovimentoCanoHorizontal -= deltaTime * 300;

            if (velocidadeQueda < MAX_VELOCITY) {
                velocidadeQueda += 0.5;
            }



            if (Gdx.input.justTouched()) {
                velocidadeQueda = -15;
                //    Gdx.app.log("Toque", "Toque na tela");
            }

            //verifica se o cano saiu inteiramente da tela
            if (posicaoMovimentoCanoHorizontal < -canoTopo.getWidth()) {
                posicaoMovimentoCanoHorizontal = larguraDispositivo;
                alturaEntreCanosRandomica = numeroRandomico.nextInt(800) - 300;
                marcouPonto = false;
            }

            //Verifica pontuaçao
            if(posicaoMovimentoCanoHorizontal < 120) {
                if( !marcouPonto ){
                pontuacao++;
                marcouPonto = true;
                }
            }

            if (posicaoInicialVertical > 0 || velocidadeQueda < 0)
                posicaoInicialVertical = posicaoInicialVertical - velocidadeQueda;
        }

        batch.begin();

        batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
        batch.draw(canoTopo, posicaoMovimentoCanoHorizontal, alturaDispositivo/ 2 + espaçoEntreCanos / 2 +  alturaEntreCanosRandomica);
        batch.draw(canoBaixo, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espaçoEntreCanos / 2 +  alturaEntreCanosRandomica);
        batch.draw(passaros[(int) variacao], 120, posicaoInicialVertical);
        fonte.draw(batch, String.valueOf(pontuacao), larguraDispositivo / 2, alturaDispositivo - 50 );

        batch.end();

        passaroCirculo.set(120 + passaros[0].getWidth() / 2, posicaoInicialVertical + passaros[0].getHeight()/2, passaros[0].getHeight()/2);

        rectangleCanoBaixo = new Rectangle(
                posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espaçoEntreCanos / 2 +  alturaEntreCanosRandomica,
                canoBaixo.getWidth(), canoBaixo.getHeight()
        );


        //desenhar formas
//        shape.begin(ShapeRenderer.ShapeType.Filled);
//        shape.circle(passaroCirculo.x, passaroCirculo.y, passaroCirculo.radius);
//       // shape.setColor(Color.RED);
//       // shape.rect(retanguloCanoBaixo.x, rectangleCanoBaixo.y, rectangleCanoBaixo.width, rectangleCanoBaixo.height);

        shape.end();
    }

}