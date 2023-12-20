package view;

/**
 * Interface for panel.
 */
public interface IPanel {

  /**
   * Message for passing move.
   */
  void passMessage();


  /**
   * Initializes hex image list.
   */
  void initializeShapeImageList();


  /**
   * Stops view.
   */
  void stopView();


  /**
   * Starts view.
   */
  void startView();
}
