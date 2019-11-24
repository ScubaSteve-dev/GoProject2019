package UI;//Class implemented by John Comeaux

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.Point;
import java.io.InputStream;
import java.util.ArrayList;

import core.Game;
import entity.Player;

public class GameBoardUI
{
	private static ArrayList<ImageView> intersects;
	public static boolean passClicked, resignClicked;
	public static Point newMove;
	private static Circle turnIndicator;
	private static Player p1, p2;
	private static Text blackPlayerPieces, whitePlayerPieces;
	
	public void startGame(Stage stage, Player o, Player t) throws Exception
	{
		p1 = o;
		p2 = t;
		BorderPane mainPane = new BorderPane();
		mainPane.setBackground(
				new Background(new BackgroundFill(Color.rgb(25, 255, 56), CornerRadii.EMPTY, Insets.EMPTY)));
		
		drawBoard(mainPane);
		drawPlayerInfo(mainPane);
		
		turnIndicator = new Circle(80, Color.BLACK);
		turnIndicator.setCenterX(670);
		turnIndicator.setCenterY(270);
		mainPane.getChildren().add(turnIndicator);
		
		Button passButton = new Button("Pass"), resignButton = new Button("Resign");
		passButton.setOnAction(e ->
		{
			passClicked = true;
		});
		resignButton.setOnAction(e ->
		{
			resignClicked = true;
		});
		
		FlowPane buttons = new FlowPane(Orientation.VERTICAL, passButton, resignButton);
		
		passButton.setPrefSize(90, 55);
		resignButton.setPrefSize(90, 55);
		buttons.setLayoutX(555);
		buttons.setLayoutY(460);
		buttons.setHgap(80);
		mainPane.getChildren().add(buttons);
		
		Game g = new Game(this, p1, p2);
		Thread gameThread = new Thread()
		{
			@Override
			public void run()
			{
				g.Start();
			}
		};
		gameThread.setDaemon(true);
		
		Thread timer = new Thread()
		{
			@Override
			public void run()
			{
				long seconds = 180;
				try
				{
					Thread.sleep(seconds * 1000);
				}
				catch (Exception e)
				{
					
				}
				Game.NumberPasses = 1;
				passClicked = true;
				Game.GameOver = true;
			}
		};
		timer.start();
		
		stage.setOnCloseRequest(e ->
		{
			Game.GameOver = true;
			// System.out.println("Start window close.");
		});
		stage.setScene(new Scene(mainPane, 850, 550));
		stage.setResizable(false);
		stage.setTitle("GO Game in Progress");
		stage.show();
		gameThread.start();
	}
	
	public void updateBoard(char[][] b)
	{
		blackPlayerPieces.setText(Integer.toString(p1.piecesLeft));
		whitePlayerPieces.setText(Integer.toString(p2.piecesLeft));
		if (turnIndicator.getFill() == Color.WHITE)
		{
			turnIndicator.setFill(Color.BLACK);
		}
		else
		{
			turnIndicator.setFill(Color.WHITE);
		}
		
		for (int x = 0; x < 19; x++)
		{
			boolean isLeft = x == 0, isRight = x == 18;
			for (int y = 0; y < 19; y++)
			{
				boolean isTop = y == 0, isBottom = y == 18;
				intersects.get(x * 19 + y)
						.setImage(new Image(getImageStream(isTop, isBottom, isLeft, isRight, b[x][y])));
			}
		}
	}
	
	private static void drawBoard(BorderPane mainPane)
	{
		intersects = new ArrayList<>();
		GridPane board = new GridPane();
		for (int x = 0; x < 19; x++)
		{
			boolean isLeft = x == 0, isRight = x == 18;
			for (int y = 0; y < 19; y++)
			{
				boolean isTop = y == 0, isBottom = y == 18;
				ImageView imageView = getImage(isTop, isBottom, isLeft, isRight);
				intersects.add(imageView);
				final int tempX = x, tempY = y;
				imageView.setOnMouseClicked(e ->
				{
					newMove = new Point(tempX, tempY);
					// Alert alert = new Alert(Alert.AlertType.INFORMATION, "X:" + tempX + " Y:" +
					// tempY,
					// ButtonType.CANCEL);
					// alert.showAndWait();
				});
				board.setPadding(new Insets(25.0));
				board.add(imageView, x, y);
			}
		}
		mainPane.setLeft(board);
	}
	
	private static void drawPlayerInfo(BorderPane mainPane)
	{
		{
			Circle blackPiecesCircle = new Circle(30, Color.BLACK);
			Text blackPlayerName = new Text(p1.playerName);
			blackPlayerPieces = new Text("181");
			
			blackPlayerName.setLayoutX(555);
			blackPlayerName.setLayoutY(55);
			
			blackPlayerPieces.setFill(Color.WHITE);
			blackPlayerPieces.setLayoutX(570);
			blackPlayerPieces.setLayoutY(90);
			
			blackPiecesCircle.setCenterX(580);
			blackPiecesCircle.setCenterY(90);
			
			mainPane.getChildren().addAll(blackPiecesCircle, blackPlayerName, blackPlayerPieces);
			
			Circle whitePiecesCircle = new Circle(30, Color.WHITE);
			Text whitePlayerName = new Text(p2.playerName);
			whitePlayerPieces = new Text("180");
			
			whitePlayerName.setLayoutX(725);
			whitePlayerName.setLayoutY(55);
			
			whitePlayerPieces.setFill(Color.BLACK);
			whitePlayerPieces.setLayoutX(740);
			whitePlayerPieces.setLayoutY(90);
			
			whitePiecesCircle.setCenterX(750);
			whitePiecesCircle.setCenterY(90);
			
			mainPane.getChildren().addAll(whitePiecesCircle, whitePlayerName, whitePlayerPieces);
		}
	}
	
	private static InputStream getImageStream(boolean isTop, boolean isBottom, boolean isLeft, boolean isRight,
			char piece)
	{
		InputStream temp;
		if (isTop)
		{
			if (isLeft)
			{
				temp = GameBoardUI.class
						.getResourceAsStream("images/Corner"
								+ (piece == ' ' ? ""
										: piece == 'B' ? "Black"
												: piece == 'b' ? "BlackGrey" : piece == 'W' ? "White" : "WhiteGrey")
								+ ".png");
			}
			else if (isRight)
			{
				temp = GameBoardUI.class
						.getResourceAsStream("images/Corner"
								+ (piece == ' ' ? ""
										: piece == 'B' ? "Black"
												: piece == 'b' ? "BlackGrey" : piece == 'W' ? "White" : "WhiteGrey")
								+ ".png");
			}
			else
			{
				temp = GameBoardUI.class
						.getResourceAsStream("images/Side"
								+ (piece == ' ' ? ""
										: piece == 'B' ? "Black"
												: piece == 'b' ? "BlackGrey" : piece == 'W' ? "White" : "WhiteGrey")
								+ ".png");
				
			}
		}
		else if (isBottom)
		{
			if (isLeft)
			{
				temp = GameBoardUI.class
						.getResourceAsStream("images/Corner"
								+ (piece == ' ' ? ""
										: piece == 'B' ? "Black"
												: piece == 'b' ? "BlackGrey" : piece == 'W' ? "White" : "WhiteGrey")
								+ ".png");
			}
			else if (isRight)
			{
				temp = GameBoardUI.class
						.getResourceAsStream("images/Corner"
								+ (piece == ' ' ? ""
										: piece == 'B' ? "Black"
												: piece == 'b' ? "BlackGrey" : piece == 'W' ? "White" : "WhiteGrey")
								+ ".png");
			}
			else
			{
				temp = GameBoardUI.class
						.getResourceAsStream("images/Side"
								+ (piece == ' ' ? ""
										: piece == 'B' ? "Black"
												: piece == 'b' ? "BlackGrey" : piece == 'W' ? "White" : "WhiteGrey")
								+ ".png");
			}
		}
		else if (isLeft)
		{
			temp = GameBoardUI.class
					.getResourceAsStream("images/Side"
							+ (piece == ' ' ? ""
									: piece == 'B' ? "Black"
											: piece == 'b' ? "BlackGrey" : piece == 'W' ? "White" : "WhiteGrey")
							+ ".png");
		}
		else if (isRight)
		{
			temp = GameBoardUI.class
					.getResourceAsStream("images/Side"
							+ (piece == ' ' ? ""
									: piece == 'B' ? "Black"
											: piece == 'b' ? "BlackGrey" : piece == 'W' ? "White" : "WhiteGrey")
							+ ".png");
		}
		else
		{
			temp = GameBoardUI.class
					.getResourceAsStream("images/Cross"
							+ (piece == ' ' ? ""
									: piece == 'B' ? "Black"
											: piece == 'b' ? "BlackGrey" : piece == 'W' ? "White" : "WhiteGrey")
							+ ".png");
		}
		return temp;
	}
	
	private static ImageView getImage(boolean isTop, boolean isBottom, boolean isLeft, boolean isRight)
	{
		ImageView temp;
		if (isTop)
		{
			if (isLeft)
			{
				temp = new ImageView(new Image(GameBoardUI.class.getResourceAsStream("images/Corner.png")));
			}
			else if (isRight)
			{
				temp = new ImageView(new Image(GameBoardUI.class.getResourceAsStream("images/Corner.png")));
				temp.setRotate(90);
			}
			else
			{
				temp = new ImageView(new Image(GameBoardUI.class.getResourceAsStream("images/Side.png")));
				
			}
		}
		else if (isBottom)
		{
			if (isLeft)
			{
				temp = new ImageView(new Image(GameBoardUI.class.getResourceAsStream("images/Corner.png")));
				temp.setRotate(-90);
			}
			else if (isRight)
			{
				temp = new ImageView(new Image(GameBoardUI.class.getResourceAsStream("images/Corner.png")));
				temp.setRotate(180);
			}
			else
			{
				temp = new ImageView(new Image(GameBoardUI.class.getResourceAsStream("images/Side.png")));
				temp.setRotate(180);
			}
		}
		else if (isLeft)
		{
			temp = new ImageView(new Image(GameBoardUI.class.getResourceAsStream("images/Side.png")));
			temp.setRotate(-90);
		}
		else if (isRight)
		{
			temp = new ImageView(new Image(GameBoardUI.class.getResourceAsStream("images/Side.png")));
			temp.setRotate(90);
		}
		else
		{
			temp = new ImageView(new Image(GameBoardUI.class.getResourceAsStream("images/Cross.png")));
		}
		return temp;
	}
	
}
