import React, {useState, useEffect} from 'react';
import PropTypes from 'prop-types';
import {lighten, makeStyles, useTheme} from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableFooter from '@material-ui/core/TableFooter';
import TablePagination from '@material-ui/core/TablePagination';
import TableRow from '@material-ui/core/TableRow';
import {
    Checkbox, Divider,
    FormControl,
    FormControlLabel, FormGroup, Grid,
    Radio,
    RadioGroup,
    TableHead,
    TableSortLabel,
    withStyles
} from "@material-ui/core";
import TablePaginationActions from "./TablePaginationActions";
import EnhancedTableHead from "./EnhancedTableHead";

function descendingComparator(a, b, orderBy) {
    if (b[orderBy] < a[orderBy]) {
        return -1;
    }
    if (b[orderBy] > a[orderBy]) {
        return 1;
    }
    return 0;
}

function getComparator(order, orderBy) {
    return order === 'desc'
        ? (a, b) => descendingComparator(a, b, orderBy)
        : (a, b) => -descendingComparator(a, b, orderBy);
}

function stableSort(array, comparator) {
    const stabilizedThis = array.map((el, index) => [el, index]);
    stabilizedThis.sort((a, b) => {
        const order = comparator(a[0], b[0]);
        if (order !== 0) return order;
        return a[1] - b[1];
    });
    return stabilizedThis.map((el) => el[0]);
}

const columnVisibilityInit = {
    id: false,
    wordSimplified: true,
    wordTraditional: false,
    pinyin: true,
    pinyinNumbered: false,
    meaning: true,
    level: true
}


const useStyles2 = makeStyles((theme) => ({
    root: {
        width: '100%',
    },
    paper: {
        width: '100%',
        marginBottom: theme.spacing(2),
    },
    table: {
        minWidth: 750,
    },
    visuallyHidden: {
        border: 0,
        clip: 'rect(0 0 0 0)',
        height: 1,
        margin: -1,
        overflow: 'hidden',
        padding: 0,
        position: 'absolute',
        top: 20,
        width: 1,
    },
    tableCell: {
        fontSize: '1.2rem',
    },
}));

export default function HSKVocabTable(props) {
    const { vocabs } = props;
    // console.log("props.vocabs: " + props.vocabs + " | vocabs: " + vocabs);
    const classes = useStyles2();
    const [rows, setRows] = useState(vocabs);
    const [isCumulative, setCumulative] = useState(true);
    const [order, setOrder] = useState('asc');
    const [orderBy, setOrderBy] = useState('calories');
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);

    const [columnVisibility, setColumnVisibility] = useState(columnVisibilityInit);

    useEffect(() => {
        setRowsPerPage(10);
        // console.log("rows.length after: " + rows.length);
    }, [rows]);

    useEffect(() => {
        setRows(isCumulative ? vocabs : vocabs.filter(vocab => vocab.level === "HSK" + props.level));
    }, [isCumulative]);

    const handleRequestSort = (event, property) => {
        const isAsc = orderBy === property && order === 'asc';
        setOrder(isAsc ? 'desc' : 'asc');
        setOrderBy(property);
    };

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(event.target.value < rows.length
            ? parseInt(event.target.value, 10)
            : (rows.length > 10 ? rows.length : 10));
        setPage(0);
    };

    const handleCumulativeChange = (event) => {
        setCumulative(!isCumulative);
    }

    const emptyRows = rowsPerPage - Math.min(rowsPerPage, rows.length - page * rowsPerPage);

    return (
        <div>
            <TableContainer>
                <Table className={classes.table} aria-label="custom pagination table">
                    <EnhancedTableHead
                        order={order}
                        orderBy={orderBy}
                        onRequestSort={handleRequestSort}
                        isCumulative={isCumulative}
                        handleCumulativeChange={handleCumulativeChange}
                        rowCount={rows.length}
                        rowsPerPage={rowsPerPage}
                        page={page}
                        handleChangePage={handleChangePage}
                        handleChangeRowsPerPage={handleChangeRowsPerPage}
                        columnVisibility={columnVisibility}
                        setColumnVisibility={setColumnVisibility}
                    />
                    <TableBody>
                        {stableSort(rows, getComparator(order, orderBy))
                            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                            .map((row, index) => {
                                const labelId = `enhanced-table-checkbox-${index}`;

                                return (
                                    <TableRow key={row.id}>
                                        {columnVisibility['id'] && <TableCell style={{fontSize: "1.0rem"}}>{row.id}</TableCell>}
                                        <TableCell component="th" id={labelId} scope="row" className={classes.tableCell}>
                                            {row.wordSimplified}
                                        </TableCell>
                                        {columnVisibility['wordTraditional'] && <TableCell style={{fontSize: "1.2rem"}}>{row.wordTraditional}</TableCell>}
                                        {columnVisibility['pinyin'] && <TableCell style={{fontSize: "1.0rem"}}>{row.pinyin}</TableCell>}
                                        {columnVisibility['pinyinNumbered'] && <TableCell style={{fontSize: "1.0rem"}}>{row.pinyinNumbered}</TableCell>}
                                        {columnVisibility['meaning'] && <TableCell style={{fontSize: "1.0rem"}}>{row.meaning}</TableCell>}
                                        {columnVisibility['level'] && <TableCell style={{fontSize: "1.0rem"}} align="right">{row.level}</TableCell>}
                                    </TableRow>
                                );
                            })}
                        {emptyRows > 0 && (
                            <TableRow style={{ height: 53 * (emptyRows <= 9 ? emptyRows : 9) }}>
                                <TableCell colSpan={6} />
                            </TableRow>
                        )}
                    </TableBody>
                    <TableFooter>
                        <TableRow>
                            <TablePagination
                                rowsPerPageOptions={[10, 25, 50, 100, 500, 1000, { label: 'All', value: rows.length }]}
                                colSpan={4}
                                count={rows.length}
                                rowsPerPage={rowsPerPage}
                                page={page}
                                SelectProps={{
                                    inputProps: { 'aria-label': 'rows per page' },
                                    native: true,
                                }}
                                onPageChange={handleChangePage}
                                onRowsPerPageChange={handleChangeRowsPerPage}
                                ActionsComponent={TablePaginationActions}
                            />
                        </TableRow>
                    </TableFooter>
                </Table>
            </TableContainer>
        </div>
    );
}
