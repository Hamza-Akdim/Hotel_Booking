import React, { useEffect, useState } from 'react'
import { getRoomTypes } from '../utils/apiFunctions'

const RoomTypeSelector = ({handleRoomInputChange, newRoom}) => {
    let [roomTypes, setRoomTypes] = useState([])
    let [showNewRoomTypeInput, setShowNewRoomTypeInput] = useState(false)
    let [newRoomType, setNewRoomType] = useState("")    

    useEffect(()=>{
        getRoomTypes()
        .then((result) => { 
            setRoomTypes(result)
        })
        .catch((err) => {
            console.log(err)
        });
    },[])

    const handleAddNewRoomType = ()=>{
        if(newRoomType!="")
        {
            setRoomTypes([...roomTypes, newRoomType])
            setNewRoomType("")
            showNewRoomTypeInput(false)
        }
    }



    return (
        <>
            {roomTypes.length > 0 && (
                <div>
                    <select 
                        required 
                        className="form-select"
                        name="roomType" 
                        onChange={(e)=>{
                            if (e.target.value === "Add New") {
                                 setShowNewRoomTypeInput(true)   
                            }
                            else{
                                handleRoomInputChange(e)
                            }
                        }}
                        value={newRoom.roomType}
                        >
                            <option value="">Select a room type</option>
                            <option value={"Add New"}>Add New</option>
                            {roomTypes.map((type, index) =>(
                                <option key={index} value={type}>
                                    {type}
                                </option>
                            ))}
                        </select>

                        {showNewRoomTypeInput && (
                            <div className="mt-2">
                                <div className="input-group">
                                    <input
                                        type="text"
                                        className="form-control"
                                        placeholder="Enter New Room Type"
                                        value={newRoomType}
                                        onChange={e=> setNewRoomType(e.target.value)}
                                    />

                                    <button className="btn btn-hotel" onClick={handleAddNewRoomType}>
                                        Add
                                    </button>
                                </div>
                            </div>
                        )}
                </div>
            )}
        </>
    ) 
}

export default RoomTypeSelector
