package net.minecraft.server;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
import org.bukkit.inventory.InventoryView;
// CraftBukkit end

public class ContainerHorse extends Container {

    private final IInventory a;
    private final EntityHorseAbstract f;

    // CraftBukkit start
    org.bukkit.craftbukkit.inventory.CraftInventoryView bukkitEntity;
    PlayerInventory player;

    @Override
    public InventoryView getBukkitView() {
        if (bukkitEntity != null) {
            return bukkitEntity;
        }

        return bukkitEntity = new CraftInventoryView(player.player.getBukkitEntity(), a.getOwner().getInventory(), this);
    }

    public ContainerHorse(IInventory iinventory, final IInventory iinventory1, final EntityHorseAbstract entityhorseabstract, EntityHuman entityhuman) {
        player = (PlayerInventory) iinventory;
        // CraftBukkit end
        this.a = iinventory1;
        this.f = entityhorseabstract;
        boolean flag = true;

        iinventory1.startOpen(entityhuman);
        boolean flag1 = true;

        this.a(new Slot(iinventory1, 0, 8, 18) {
            public boolean isAllowed(ItemStack itemstack) {
                return itemstack.getItem() == Items.SADDLE && !this.hasItem() && entityhorseabstract.dF();
            }
        });
        this.a(new Slot(iinventory1, 1, 8, 36) {
            public boolean isAllowed(ItemStack itemstack) {
                return entityhorseabstract.f(itemstack);
            }

            public int getMaxStackSize() {
                return 1;
            }
        });
        int i;
        int j;

        if (entityhorseabstract instanceof EntityHorseChestedAbstract && ((EntityHorseChestedAbstract) entityhorseabstract).isCarryingChest()) {
            for (i = 0; i < 3; ++i) {
                for (j = 0; j < ((EntityHorseChestedAbstract) entityhorseabstract).dt(); ++j) {
                    this.a(new Slot(iinventory1, 2 + j + i * ((EntityHorseChestedAbstract) entityhorseabstract).dt(), 80 + j * 18, 18 + i * 18));
                }
            }
        }

        for (i = 0; i < 3; ++i) {
            for (j = 0; j < 9; ++j) {
                this.a(new Slot(iinventory, j + i * 9 + 9, 8 + j * 18, 102 + i * 18 + -18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.a(new Slot(iinventory, i, 8 + i * 18, 142));
        }

    }

    public boolean canUse(EntityHuman entityhuman) {
		return this.a.a(entityhuman) && this.f.isAlive() && this.f.valid && this.f.g((Entity) entityhuman) < 8.0F; // NeonPaper! - Fix MC-161754
    }

    public ItemStack shiftClick(EntityHuman entityhuman, int i) {
        ItemStack itemstack = ItemStack.a;
        Slot slot = (Slot) this.slots.get(i);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();

            itemstack = itemstack1.cloneItemStack();
            if (i < this.a.getSize()) {
                if (!this.a(itemstack1, this.a.getSize(), this.slots.size(), true)) {
                    return ItemStack.a;
                }
            } else if (this.getSlot(1).isAllowed(itemstack1) && !this.getSlot(1).hasItem()) {
                if (!this.a(itemstack1, 1, 2, false)) {
                    return ItemStack.a;
                }
            } else if (this.getSlot(0).isAllowed(itemstack1)) {
                if (!this.a(itemstack1, 0, 1, false)) {
                    return ItemStack.a;
                }
            } else if (this.a.getSize() <= 2 || !this.a(itemstack1, 2, this.a.getSize(), false)) {
                return ItemStack.a;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.a);
            } else {
                slot.f();
            }
        }

        return itemstack;
    }

    public void b(EntityHuman entityhuman) {
        super.b(entityhuman);
        this.a.closeContainer(entityhuman);
    }
}
