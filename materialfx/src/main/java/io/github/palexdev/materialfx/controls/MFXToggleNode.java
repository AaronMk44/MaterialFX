package io.github.palexdev.materialfx.controls;

import io.github.palexdev.materialfx.MFXResourcesLoader;
import io.github.palexdev.materialfx.controls.enums.ToggleNodeShape;
import io.github.palexdev.materialfx.effects.RippleGenerator;
import javafx.css.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.skin.ToggleButtonSkin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.List;

public class MFXToggleNode extends ToggleButton {
    //================================================================================
    // Properties
    //================================================================================
    private static final StyleablePropertyFactory<MFXToggleNode> FACTORY = new StyleablePropertyFactory<>(ToggleButton.getClassCssMetaData());
    private final String STYLECLASS = "mfx-toggle-node";
    private final String STYLESHEET = MFXResourcesLoader.load("css/mfx-togglenode.css").toString();
    private final RippleGenerator rippleGenerator = new RippleGenerator(this);

    //================================================================================
    // Constructors
    //================================================================================
    public MFXToggleNode() {
        setText("");
        initialize();
    }

    public MFXToggleNode(String text) {
        super(text);
        initialize();
    }

    public MFXToggleNode(Node graphic) {
        super("", graphic);
        initialize();
    }

    public MFXToggleNode(String text, Node graphic) {
        super("", graphic);
        initialize();
    }

    //================================================================================
    // Methods
    //================================================================================
    private void initialize() {
        getStyleClass().add(STYLECLASS);
        setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        rippleGenerator.setAnimateBackground(false);
        rippleGenerator.setRippleColor(Color.GRAY);
        rippleGenerator.setInDuration(Duration.millis(350));

        prefWidthProperty().bind(size);
        prefHeightProperty().bind(size);
        clip();

        addListeners();
        setSize(40);
    }

    /**
     * Adds listener for toggleShapeProperty, sizeProperty and selectedProperty
     */
    private void addListeners() {
        toggleShapeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(ToggleNodeShape.CIRCLE)) {
                prefWidthProperty().bind(size);
                prefHeightProperty().bind(size);
                clip();
            } else {
                setClip(null);
                prefWidthProperty().bind(size.multiply(3.5));
                prefHeightProperty().bind(size);
            }
        });

        sizeProperty().addListener((sObservable, sOldValue, sNewValue) -> {
            if (sNewValue.doubleValue() != sOldValue.doubleValue()) {
                setSize(sNewValue.doubleValue());
            }
        });

        selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                setBackground(new Background(new BackgroundFill(selectedColor.get(), CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                setBackground(new Background(new BackgroundFill(unSelectedColor.get(), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });
    }

    /**
     * Resets the clip
     */
    private void clip() {
        setClip(null);
        Circle circle = new Circle(getSize() * 0.5);
        circle.centerXProperty().bind(widthProperty().divide(2.0));
        circle.centerYProperty().bind(heightProperty().divide(2.0));
        setClip(circle);
    }

    //================================================================================
    // Styleable properties
    //================================================================================

    /**
     * Specifies the size (both width and height) of the control.
     */
    private final StyleableDoubleProperty size = new SimpleStyleableDoubleProperty(
            StyleableProperties.SIZE,
            this,
            "size",
            40.0
    );

    /**
     * Specifies the shape of the control
     */
    private final StyleableObjectProperty<ToggleNodeShape> toggleShape = new SimpleStyleableObjectProperty<>(
            StyleableProperties.SHAPE,
            this,
            "toggleShape",
            ToggleNodeShape.CIRCLE
    );

    /**
     * Specifies the background color when selected.
     *
     * @see Color
     */
    private final StyleableObjectProperty<Paint> selectedColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.SELECTED_COLOR,
            this,
            "selectedColor",
            Color.rgb(190, 190, 190, 0.5)
    );

    /**
     * Specifies the background color when unselected.
     *
     * @see Color
     */
    private final StyleableObjectProperty<Paint> unSelectedColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.UNSELECTED_COLOR,
            this,
            "unSelectedColor",
            Color.TRANSPARENT
    );

    public double getSize() {
        return size.get();
    }

    public StyleableDoubleProperty sizeProperty() {
        return size;
    }

    public void setSize(double size) {
        this.size.set(size);
    }

    public ToggleNodeShape getToggleShape() {
        return toggleShape.get();
    }

    public StyleableObjectProperty<ToggleNodeShape> toggleShapeProperty() {
        return toggleShape;
    }

    public void setToggleShape(ToggleNodeShape toggleShape) {
        this.toggleShape.set(toggleShape);
    }

    public Paint getSelectedColor() {
        return selectedColor.get();
    }

    public StyleableObjectProperty<Paint> selectedColorProperty() {
        return selectedColor;
    }

    public void setSelectedColor(Paint selectedColor) {
        this.selectedColor.set(selectedColor);
    }

    public Paint getUnSelectedColor() {
        return unSelectedColor.get();
    }

    public StyleableObjectProperty<Paint> unSelectedColorProperty() {
        return unSelectedColor;
    }

    public void setUnSelectedColor(Paint unSelectedColor) {
        this.unSelectedColor.set(unSelectedColor);
    }

    //================================================================================
    // CssMetaData
    //================================================================================
    private static class StyleableProperties {
        private static final List<CssMetaData<? extends Styleable, ?>> cssMetaDataList;

        private static final CssMetaData<MFXToggleNode, Number> SIZE =
        FACTORY.createSizeCssMetaData(
                "-mfx-size",
                MFXToggleNode::sizeProperty,
                40
        );

        private static final CssMetaData<MFXToggleNode, ToggleNodeShape> SHAPE =
                FACTORY.createEnumCssMetaData(
                        ToggleNodeShape.class,
                        "-mfx-shape",
                        MFXToggleNode::toggleShapeProperty,
                        ToggleNodeShape.CIRCLE
                );

        private static final CssMetaData<MFXToggleNode, Paint> SELECTED_COLOR =
                FACTORY.createPaintCssMetaData(
                        "-mfx-selected-color",
                        MFXToggleNode::selectedColorProperty,
                        Color.rgb(0, 0, 0, 0.2)
                );

        private static final CssMetaData<MFXToggleNode, Paint> UNSELECTED_COLOR =
                FACTORY.createPaintCssMetaData(
                        "-mfx-unselected-color",
                        MFXToggleNode::unSelectedColorProperty,
                        Color.TRANSPARENT
                );

        static {
            cssMetaDataList = List.of(SIZE, SHAPE, SELECTED_COLOR, UNSELECTED_COLOR);
        }
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.cssMetaDataList;
    }

    //================================================================================
    // Override Methods
    //================================================================================

    @Override
    protected Skin<?> createDefaultSkin() {
        ToggleButtonSkin skin = new ToggleButtonSkin(this);
        this.getChildren().add(0, rippleGenerator);
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            rippleGenerator.setGeneratorCenterX(event.getX());
            rippleGenerator.setGeneratorCenterY(event.getY());
            rippleGenerator.createRipple();
        });
        return skin;
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getClassCssMetaData();
    }
}
