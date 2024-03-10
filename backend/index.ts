import express, {Request, Response} from 'express'

const app = express();

app.use(express.json());

app.get('/', (req: Request, res: Response) => {
    res.send('Welcome to the RESTful API!');
});

app.listen(3000, () => {
    console.log('Server is up and running on port 3000')
})