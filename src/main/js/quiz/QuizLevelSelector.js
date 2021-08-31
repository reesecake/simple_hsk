import {FormControl, InputLabel, Select} from "@material-ui/core";
import React from "react";

export default function QuizLevelSelector(props) {
    const { level, updateQuizLevel } = props;

    return (
        <FormControl variant="outlined">
            <InputLabel htmlFor="outlined-level-native-simple">Level</InputLabel>
            <Select
                native
                value={level}
                onChange={updateQuizLevel}
                label="Level"
                inputProps={{
                    name: 'level',
                    id: 'outlined-level-native-simple',
                }}
            >
                <option value={1}>HSK1</option>
                <option value={2}>HSK2</option>
                <option value={3}>HSK3</option>
                <option value={4}>HSK4</option>
                <option value={5}>HSK5</option>
                <option value={6}>HSK6</option>
            </Select>
        </FormControl>
    );
}
