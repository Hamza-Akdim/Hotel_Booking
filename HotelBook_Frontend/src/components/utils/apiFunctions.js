import axios from "axios"

export const api = axios.create({
    baseURL :"http://localhost:8080"
})

export async function addRoom(photo, roomType, roomPrice) {
    const formData = new FormData()
    formData.append("photo", photo)
    formData.append("roomType", roomType)
    formData.append("roomPrice", roomPrice)


    const response = await api.post("/rooms/add/new-room", formData)

    if(response.status === 200) return true
    
    return false
}

export async function getRoomTypes() {
    try {
        const reponse = await api.get("/rooms/room-types")
        return reponse.data
    } catch (error) {
        throw new Error("Error fetching room types")
    }
}

