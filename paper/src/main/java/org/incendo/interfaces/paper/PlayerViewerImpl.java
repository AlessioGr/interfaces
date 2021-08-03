package org.incendo.interfaces.paper;

import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.interfaces.core.view.InterfaceView;
import org.incendo.interfaces.paper.element.text.TextElement;
import org.incendo.interfaces.paper.view.BookView;
import org.incendo.interfaces.paper.view.ChatView;
import org.incendo.interfaces.paper.view.ChestView;
import org.incendo.interfaces.paper.view.CombinedView;
import org.incendo.interfaces.paper.view.PlayerInventoryView;

import java.util.List;

final class PlayerViewerImpl implements PlayerViewer {

    private final @NonNull Player player;

    /**
     * Constructs {@code PlayerViewer}.
     *
     * @param player the player
     */
    PlayerViewerImpl(final @NonNull Player player) {
        this.player = player;
    }

    private void openChestView(final @NonNull ChestView chestView) {
        this.player.openInventory(chestView.getInventory());
    }

    private void openCombinedView(final @NonNull CombinedView combinedView) {
        this.player.openInventory(combinedView.getInventory());
    }

    private void openBookView(final @NonNull BookView bookView) {
        this.player.openBook(bookView.book());
    }

    private void openChatView(final @NonNull ChatView chatView) {
        final @NonNull List<TextElement> elements = chatView.pane().textElements();

        for (final @NonNull TextElement element : elements) {
            this.player.sendMessage(element.text()); // won't compile
        }
    }

    private void openPlayerView(final @NonNull PlayerInventoryView inventoryView) {
        inventoryView.open();
    }

    @Override
    public void open(final @NonNull InterfaceView<?, ?> view) {
        if (view instanceof ChestView) {
            this.openChestView((ChestView) view);
        } else if (view instanceof CombinedView) {
            this.openCombinedView((CombinedView) view);
        } else if (view instanceof BookView) {
            this.openBookView((BookView) view);
        } else if (view instanceof PlayerInventoryView) {
            this.openPlayerView((PlayerInventoryView) view);
        } else if (view instanceof ChatView) {
            this.openChatView((ChatView) view);
        } else {
            throw new UnsupportedOperationException("Cannot open view of type " + view.getClass().getName() + ".");
        }
    }

    @Override
    public void close() {
        this.player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
    }

    @Override
    public @NonNull Player player() {
        return this.player;
    }

}
