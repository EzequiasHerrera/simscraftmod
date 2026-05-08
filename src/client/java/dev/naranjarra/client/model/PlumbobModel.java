package dev.naranjarra.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.player.Player;

public class PlumbobModel extends EntityModel<Player> {
	private final ModelPart main;

	public PlumbobModel(ModelPart root) {
		// En 1.21 se usa RenderType para definir cómo se ve la textura
		super(RenderType::getEntityCutoutNoCull);
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("main", CubeListBuilder.create()
						.texOffs(7, 0).mirror().addBox(-1.25F, -30.0F, -1.25F, 2.5F, 3.0F, 2.5F, new CubeDeformation(0.0F)).mirror(false)
						.texOffs(3, 6).mirror().addBox(-2.25F, -27.0F, -2.25F, 4.5F, 3.0F, 4.5F, new CubeDeformation(0.0F)).mirror(false)
						.texOffs(0, 14).addBox(-3.0F, -24.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
						.texOffs(3, 6).mirror().addBox(-2.25F, -22.0F, -2.25F, 4.5F, 3.0F, 4.5F, new CubeDeformation(0.0F)).mirror(false)
						.texOffs(7, 0).mirror().addBox(-1.25F, -19.0F, -1.25F, 2.5F, 3.0F, 2.5F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Player entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// 🔄 Rotación automática: girará sobre su propio eje Y basado en el tiempo (ageInTicks)
		this.main.yRot = ageInTicks * 0.1f;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		// En 1.21.2 el método renderToBuffer cambió y recibe el color como un INT
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}