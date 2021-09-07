import React, {useState} from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import DialogTitle from '@material-ui/core/DialogTitle';
import Dialog from '@material-ui/core/Dialog';
import SettingsIcon from "@material-ui/icons/Settings";
import {
    Checkbox,
    FormControl,
    FormControlLabel,
    FormLabel,
    IconButton,
    Radio,
    RadioGroup,
    Tooltip
} from "@material-ui/core";

const answerTypeOptions = [
    {value: "meaning", label: "English meaning"},
    {value: "wordSimplified", label: "Word (Simplified)"},
    {value: "wordTraditional", label: "Word (Traditional)"},
];
const useStyles = makeStyles({
    root: {
    },
    form: {
        display: "flex",
        flexDirection: "column",
        margin: "10px",
    },
});

function SimpleDialog(props) {
    const classes = useStyles();
    const { onClose, open, setNeedsReload, includePinyin, setIncludePinyin, answerType, setAnswerType } = props;

    const [isCumulative, setCumulative] = useState(false);


    const handleClose = () => {
        onClose();
    };

    const handleCheck = () => {
        setCumulative(!isCumulative);
    }

    const handleIncludePinyin = () => {
        setIncludePinyin(!includePinyin);
        setNeedsReload(true);  // TODO: could be improved to be conditional
    }

    const handleAnswerTypeChange = (event) => {
        setAnswerType(event.target.value);
        setNeedsReload(true);
    };

    return (
        <Dialog onClose={handleClose} aria-labelledby="simple-dialog-title" open={open} fullWidth maxWidth={"xs"}>
            <DialogTitle id="simple-dialog-title">Quiz Settings</DialogTitle>
            <form className={classes.form}>
                <FormControl component={"fieldset"}>
                    <FormLabel component={"legend"}>Vocab filter</FormLabel>
                    <FormControlLabel
                        style={{marginLeft: '0px', marginBottom: '0px', }}
                        control={<Checkbox checked={isCumulative} onChange={handleCheck} name="checkedCumu" />}
                        label="Cumulative"
                    />
                    <FormControlLabel
                        style={{marginLeft: '0px', marginBottom: '0px', }}
                        control={<Checkbox checked={includePinyin} onChange={handleIncludePinyin} name="checkedPinyin" />}
                        label="Include pinyin"
                    />
                </FormControl>

                <FormControl component="fieldset">
                    <FormLabel component="legend">Answer with:</FormLabel>
                    <RadioGroup aria-label="answerType" name="answerTypeRadio" value={answerType} onChange={handleAnswerTypeChange}>
                        {answerTypeOptions.map((answerTypeOption) => (
                            <FormControlLabel key={answerTypeOption.value}
                                              value={answerTypeOption.value}
                                              control={<Radio />}
                                              label={answerTypeOption.label}
                            />
                        ))}
                    </RadioGroup>
                </FormControl>
            </form>
        </Dialog>
    );
}

SimpleDialog.propTypes = {
    onClose: PropTypes.func.isRequired,
    open: PropTypes.bool.isRequired,
};

export default function QuizOptions(props) {
    const { setNeedsReload, includePinyin, setIncludePinyin, answerType, setAnswerType } = props;

    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <div>
            <Tooltip title="Options">
                <IconButton aria-label="options" onClick={handleClickOpen}>
                    <SettingsIcon />
                </IconButton>
            </Tooltip>
            <SimpleDialog
                open={open}
                onClose={handleClose}
                setNeedsReload={setNeedsReload}
                includePinyin={includePinyin}
                setIncludePinyin={setIncludePinyin}
                answerType={answerType}
                setAnswerType={setAnswerType}
            />
        </div>
    );
}
