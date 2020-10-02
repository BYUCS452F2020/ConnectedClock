package Zone

class ZoneResources {
    companion object {
        val GROUP_1_ID = "98729fce-0809-43fe-b953-f48b14b07616"

        val GROUP_1_ZONES = listOf(
            Zone(
                "0174a780-c11a-460f-bbb4-66a135eddd97",
                40.238631,
                -111.661309,
                90.15,
                "12a20cb0-9e82-4d88-98f2-faaa9ff8c675"
            ), /* Smiths Grocery */
            Zone(
                "1b92628d-a46e-400f-bfd8-c40af4b9eaa9",
                40.252036,
                -111.649204,
                524.52,
                "ec488303-1152-4d8d-af55-db9b323be17e"
            ), /* BYU Campus */
            Zone(
                "991f5783-0e51-4cd1-9ee3-2f2d72909bfb",
                40.252979,
                -111.670495,
                93.83,
                "12a20cb0-9e82-4d88-98f2-faaa9ff8c675"
            ), /* Deseret Industries */
            Zone(
                "b821c605-182d-472c-a0df-cf0d838d77b9",
                40.234978,
                -111.668599,
                46.82,
                "04e3b648-c3dd-41da-a430-1dacde995b7d"
            ), /* Sherwin-Williams Paint */
            Zone(
                "d00edf46-a5af-4dbc-a63d-ed6d6c2b2914",
                40.275056,
                -111.680827,
                224.96,
                "12a20cb0-9e82-4d88-98f2-faaa9ff8c675"
            ), /* University Place Mall */
            Zone(
                "dffb8b27-ee34-41d9-8d54-37a08c7c300f",
                40.242857,
                -111.668508,
                102.46,
                "7be3c43c-9f4b-4f6d-bbeb-e639e8331ab9"
            ), /* Apartment */
            Zone(
                "e3d835fa-1d1f-4c23-8f70-85dc6d39b68c",
                40.272636,
                -111.710176,
                172.27,
                "12a20cb0-9e82-4d88-98f2-faaa9ff8c675"
            ) /* Orem Walmart */
        )
        val GROUP_1_GET_ZONES_RESPONSE = GetZonesResponse(GROUP_1_ZONES)

        val GROUP_2_ID = "2bc8f348-fce4-4df6-9795-deff8e721c7a"

        val GROUP_2_ZONES = listOf(
            Zone(
                "021706f8-8a4f-4408-9180-ef7e4457bf96",
                40.247222,
                -111.665663,
                317.29,
                "32f85320-92e0-4382-a5ae-d71b562422c5"
            ), /* Hospital */
            Zone(
                "679a3f92-131b-4fdb-be27-590cb5210bac",
                40.234351,
                -111.647619,
                118.45,
                "ed9ceea8-b059-4e00-b35e-83be6e63c497"
            ), /* Elementary School */
            Zone(
                "a7a71489-0506-4589-965f-ad8f32a7fc12",
                40.261547,
                -111.660352,
                185.72,
                "92a3db1e-99b6-45d1-92e6-2c47720e620e"
            ) /* Wyview Apartments */
        )
        val GROUP_2_GET_ZONES_RESPONSE = GetZonesResponse(GROUP_2_ZONES)
    }
}