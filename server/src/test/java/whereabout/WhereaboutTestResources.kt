package whereabout

class WhereaboutTestResources {
    companion object {
        val GROUP_1_WHEREABOUTS = listOf<Whereabout>(
            Whereabout("3ea0f56f-7864-4d49-a5ed-5f741a7969c8", "test1", 0, "7be3c43c-9f4b-4f6d-bbeb-e639e8331ab9"),
            Whereabout("4c43ddce-64db-4c89-9797-909dcbd0a425", "test2", 1, null)
        )

        val GROUP_1_ZONE_1_ID = "0174a780-c11a-460f-bbb4-66a135eddd97"
        val GROUP_1_ZONE_2_ID = "1b92628d-a46e-400f-bfd8-c40af4b9eaa9"
        val GROUP_1_ZONE_3_ID = "991f5783-0e51-4cd1-9ee3-2f2d72909bfb"

        val GROUP_2_WHEREABOUTS = listOf<Whereabout>(
            Whereabout("f3b55f05-c33a-4ab2-9aa7-29ad8f6efa3d", "test3", 0, "ed9ceea8-b059-4e00-b35e-83be6e63c497")
        )

        val GROUP_2_ZONE_1_ID = "021706f8-8a4f-4408-9180-ef7e4457bf96"
        val GROUP_2_ZONE_2_ID = "679a3f92-131b-4fdb-be27-590cb5210bac"
        val GROUP_2_ZONE_3_ID = "a7a71489-0506-4589-965f-ad8f32a7fc12"
    }
}