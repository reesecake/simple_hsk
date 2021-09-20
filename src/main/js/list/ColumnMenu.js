import React, {useState, useEffect, useRef} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import {
    ClickAwayListener,
    FormControl,
    FormControlLabel, FormGroup,
    FormLabel,
    Grow,
    Paper,
    Popper, Switch
} from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
    typography: {
        padding: theme.spacing(2),
    },
    popperPaper: {
        padding: "8px",
    },
    formLabel: {
        padding: "1px 8px 1px 7px",
    },
}));

export default function ColumnMenu(props) {
    const { open, setOpen, anchorRef, headCells, columnVisibility, setColumnVisibility } = props;
    const classes = useStyles();

    const handleChange = (event) => {
        setColumnVisibility({
            ...columnVisibility,
            [event.target.name]: event.target.checked }
        );
    };

    // const handleToggle = () => {
    //     setOpen((prevOpen) => !prevOpen);
    // };

    const handleClose = (event) => {
        if (anchorRef.current && anchorRef.current.contains(event.target)) {
            return;
        }

        setOpen(false);
    };

    function handleListKeyDown(event) {
        if (event.key === 'Tab') {
            event.preventDefault();
            setOpen(false);
        }
    }

    // return focus to the button when we transitioned from !open -> open
    const prevOpen = useRef(open);
    useEffect(() => {
        if (prevOpen.current === true && open === false) {
            anchorRef.current.focus();
        }

        prevOpen.current = open;
    }, [open]);

    return (
        <Popper placement={"bottom-start"} open={open} anchorEl={anchorRef.current} role={undefined} transition disablePortal>
            {({ TransitionProps, placement }) => (
                <Grow
                    {...TransitionProps}
                    style={{ transformOrigin: 'left top' }}
                >
                    <Paper className={classes.popperPaper} >
                        <ClickAwayListener onClickAway={handleClose}>
                            <FormControl component="fieldset">
                                <FormLabel component="legend">Show Columns</FormLabel>
                                <FormGroup>
                                    {headCells.map((headCell) => (
                                        <FormControlLabel
                                            key={headCell.id}
                                            className={classes.formLabel}
                                            control={<Switch size={"small"}
                                                             checked={columnVisibility[headCell.id]}
                                                             onChange={handleChange}
                                                             name={headCell.id} />}
                                            label={headCell.label}
                                        />
                                    ))}
                                </FormGroup>
                            </FormControl>
                        </ClickAwayListener>
                    </Paper>
                </Grow>
            )}
        </Popper>
    );
}
